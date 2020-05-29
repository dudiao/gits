package xyz.gits.boot.security.login.extend;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.userdetails.cache.NullUserCache;
import org.springframework.util.Assert;
import xyz.gits.boot.common.core.enums.LoginType;
import xyz.gits.boot.common.security.LoginUser;

/**
 * @author songyinyin
 * @date 2020/5/4 下午 08:53
 */
@Slf4j
public class ExtendAuthenticationProvider implements AuthenticationProvider, InitializingBean, MessageSourceAware {

    // ~ Instance fields
    // ================================================================================================

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private UserCache userCache = new NullUserCache();
    private boolean forcePrincipalAsString = false;
    protected boolean hideUserNotFoundExceptions = true;
    private UserDetailsChecker preAuthenticationChecks = new DefaultPreAuthenticationChecks();
    private UserDetailsChecker postAuthenticationChecks = new DefaultPostAuthenticationChecks();
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    private ExtendUserDetailsService extendUserDetailsService;

// ~ Methods
    // ========================================================================================================

    /**
     * 允许子类进行其他身份检查
     */
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  ExtendAuthenticationToken authentication)
            throws AuthenticationException {

    }

    @Override
    public final void afterPropertiesSet() throws Exception {
        Assert.notNull(this.userCache, "A user cache must be set");
        Assert.notNull(this.messages, "A message source must be set");
        doAfterPropertiesSet();
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        Assert.isInstanceOf(ExtendAuthenticationToken.class, authentication,
                () -> messages.getMessage(
                        "ExtendAuthenticationProvider.onlySupports",
                        "Only ExtendAuthenticationToken is supported"));

        ExtendAuthenticationToken extendAuthenticationToken = (ExtendAuthenticationToken) authentication;
        // Determine extendKey
        String extendKey = extendAuthenticationToken.getExtendKey();

        boolean cacheWasUsed = true;
        UserDetails user = this.userCache.getUserFromCache(extendKey);

        if (user == null) {
            cacheWasUsed = false;

            try {
                user = retrieveUser(extendKey,
                        (ExtendAuthenticationToken) authentication);
            } catch (UsernameNotFoundException notFound) {
                log.debug("User '" + extendKey + "' not found");

                if (hideUserNotFoundExceptions) {
                    throw new BadCredentialsException(messages.getMessage(
                            "ExtendAuthenticationProvider.badCredentials",
                            "Bad credentials"));
                } else {
                    throw notFound;
                }
            }

            Assert.notNull(user,
                    "retrieveUser returned null - a violation of the interface contract");
        }

        try {
            // 前置校验
            preAuthenticationChecks.check(user);
            additionalAuthenticationChecks(user,
                    (ExtendAuthenticationToken) authentication);
        } catch (AuthenticationException exception) {
            if (cacheWasUsed) {
                // There was a problem, so try again after checking
                // we're using latest data (i.e. not from the cache)
                cacheWasUsed = false;
                user = retrieveUser(extendKey,
                        (ExtendAuthenticationToken) authentication);
                preAuthenticationChecks.check(user);
                additionalAuthenticationChecks(user,
                        (ExtendAuthenticationToken) authentication);
            } else {
                throw exception;
            }
        }

        // 后置校验
        postAuthenticationChecks.check(user);

        if (!cacheWasUsed) {
            this.userCache.putUserInCache(user);
        }

        Object principalToReturn = user;

        if (forcePrincipalAsString) {
            principalToReturn = user.getUsername();
        }

        return createSuccessAuthentication(principalToReturn, extendAuthenticationToken, user);
    }

    /**
     * Creates a successful {@link Authentication} object.
     * <p>
     * Protected 子类可以覆盖.
     * </p>
     * <p>
     * 子类可以更新 UserDetails，比如用户头像、昵称等
     * </p>
     *
     * @param principal      that should be the principal in the returned object (defined by
     *                       the {@link #isForcePrincipalAsString()} method)
     * @param authentication that was presented to the provider for validation
     * @param user           that was loaded by the implementation
     * @return the successful authentication token
     */
    protected Authentication createSuccessAuthentication(Object principal,
                                                         ExtendAuthenticationToken authentication, UserDetails user) {

        ExtendAuthenticationToken result = new ExtendAuthenticationToken(
                authentication.getExtendKey(), authentication.getExtendType(),
                principal, authoritiesMapper.mapAuthorities(user.getAuthorities()));
        result.setDetails(authentication.getDetails());

        return result;
    }

    protected void doAfterPropertiesSet() throws Exception {
    }

    public UserCache getUserCache() {
        return userCache;
    }

    public boolean isForcePrincipalAsString() {
        return forcePrincipalAsString;
    }

    public boolean isHideUserNotFoundExceptions() {
        return hideUserNotFoundExceptions;
    }

    /**
     * 获取用户详情
     */
    protected UserDetails retrieveUser(String extendKey,
                                       ExtendAuthenticationToken authentication)
            throws AuthenticationException {
        try {
            UserDetails loadedUser = this.getExtendUserDetailsService().loadUserByExtendKey(authentication);
            if (loadedUser == null) {
                throw new InternalAuthenticationServiceException(
                        "UserDetailsService returned null, which is an interface contract violation");
            }
            return loadedUser;
        }
        catch (UsernameNotFoundException | InternalAuthenticationServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
        }
    }

    public void setForcePrincipalAsString(boolean forcePrincipalAsString) {
        this.forcePrincipalAsString = forcePrincipalAsString;
    }

    /**
     * 默认情况下，如果 extendKey 没有找到 <code>ExtendAuthenticationProvider</code> 会抛出一个
     * <code>BadCredentialsException</code>。设置这个属性为 <code>false</code> 将会抛出
     * <code>UsernameNotFoundException</code>，提示一下，这是不安全的。
     */
    public void setHideUserNotFoundExceptions(boolean hideUserNotFoundExceptions) {
        this.hideUserNotFoundExceptions = hideUserNotFoundExceptions;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    public void setUserCache(UserCache userCache) {
        this.userCache = userCache;
    }

    /**
     * 此处判断 authentication 是否是该 Provider 对应的 Token 的子类或者子接口，只有通过了，才会调用 authenticate 方法去认证
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (ExtendAuthenticationToken.class
                .isAssignableFrom(authentication));
    }

    protected UserDetailsChecker getPreAuthenticationChecks() {
        return preAuthenticationChecks;
    }

    /**
     * Sets the policy will be used to verify the status of the loaded
     * <tt>UserDetails</tt> <em>before</em> validation of the credentials takes place.
     *
     * @param preAuthenticationChecks strategy to be invoked prior to authentication.
     */
    public void setPreAuthenticationChecks(UserDetailsChecker preAuthenticationChecks) {
        this.preAuthenticationChecks = preAuthenticationChecks;
    }

    protected UserDetailsChecker getPostAuthenticationChecks() {
        return postAuthenticationChecks;
    }

    public void setPostAuthenticationChecks(UserDetailsChecker postAuthenticationChecks) {
        this.postAuthenticationChecks = postAuthenticationChecks;
    }

    public void setAuthoritiesMapper(GrantedAuthoritiesMapper authoritiesMapper) {
        this.authoritiesMapper = authoritiesMapper;
    }

    private class DefaultPreAuthenticationChecks implements UserDetailsChecker {
        @Override
        public void check(UserDetails user) {
            if (!user.isAccountNonLocked()) {
                log.debug("User account is locked");

                throw new LockedException(messages.getMessage(
                        "ExtendAuthenticationProvider.locked",
                        "User account is locked"));
            }

            if (!user.isEnabled()) {
                log.debug("User account is disabled");

                throw new DisabledException(messages.getMessage(
                        "ExtendAuthenticationProvider.disabled",
                        "User is disabled"));
            }

            if (!user.isAccountNonExpired()) {
                log.debug("User account is expired");

                throw new AccountExpiredException(messages.getMessage(
                        "ExtendAuthenticationProvider.expired",
                        "User account has expired"));
            }
        }
    }

    private class DefaultPostAuthenticationChecks implements UserDetailsChecker {
        @Override
        public void check(UserDetails user) {
            LoginUser loginUser = (LoginUser) user;
            if (!LoginType.PASSWORD.equals(loginUser.getLoginType())) {
                return;
            }
            if (!user.isCredentialsNonExpired()) {
                log.debug("User account credentials have expired");

                throw new CredentialsExpiredException(messages.getMessage(
                        "ExtendAuthenticationProvider.credentialsExpired",
                        "User credentials have expired"));
            }
        }
    }

    public void setExtendUserDetailsService(ExtendUserDetailsService extendUserDetailsService) {
        this.extendUserDetailsService = extendUserDetailsService;
    }

    protected ExtendUserDetailsService getExtendUserDetailsService() {
        if (null == extendUserDetailsService) {
            throw new InternalAuthenticationServiceException(
                    "ExtendUserDetailsService is null, 请实现该接口，并使其成为spring bean.");
        }
        return extendUserDetailsService;
    }

}

package xyz.gits.boot.common.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.web.http.HttpSessionIdResolver;
import xyz.gits.boot.api.system.service.AuthService;
import xyz.gits.boot.common.security.hander.AnonymousAuthenticationEntryPoint;
import xyz.gits.boot.common.security.hander.InvalidSessionHandler;
import xyz.gits.boot.common.security.hander.LoginUserAccessDeniedHandler;
import xyz.gits.boot.common.security.hander.SessionInformationExpiredHandler;

/**
 * <pre>@EnableGlobalMethodSecurity</pre>
 * <p>
 * 'prePostEnabled = true' --> 使{@link PreAuthorize PostAuthorize} {@link PostAuthorize}和{@link PermissionService}生效 <br/>
 * <p>
 * 'securedEnabled = true' --> 使{@link Secured}生效
 * </p>
 *
 * @author songyinyin
 * @date 2020/4/27 下午 03:57
 */
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(PermitAllUrlProperties.class)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class GitsSecurityAutoConfiguration {

    /**
     * 用户加密方式
     */
    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
        // 委托密码编码器，默认使用是 bcrypt 算法加密
        DelegatingPasswordEncoder passwordEncoder = (DelegatingPasswordEncoder) PasswordEncoderFactories.createDelegatingPasswordEncoder();
        // 设置默认加密算法 TODO 测试方便，默认不加密
//        passwordEncoder.setDefaultPasswordEncoderForMatches(new BCryptPasswordEncoder());
        passwordEncoder.setDefaultPasswordEncoderForMatches(NoOpPasswordEncoder.getInstance());
        return passwordEncoder;
    }

    @Bean
    public NullUserDetailsServiceImpl userDetailsService() {
        return new NullUserDetailsServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public AnonymousAuthenticationEntryPoint anonymousAuthenticationEntryPoint() {
        return new AnonymousAuthenticationEntryPoint();
    }

    @Bean
    @ConditionalOnMissingBean
    public InvalidSessionHandler invalidSessionHandler() {
        return new InvalidSessionHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public SessionInformationExpiredHandler sessionInformationExpiredHandler() {
        return new SessionInformationExpiredHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public LoginUserAccessDeniedHandler loginUserAccessDeniedHandler() {
        return new LoginUserAccessDeniedHandler();
    }

    @Bean(name = "ps")
    @ConditionalOnMissingBean
    public PermissionService permissionService() {
        return new PermissionService();
    }

    /**
     * 自定义session id 存放路径，支持cookie，header 和 request parameter
     */
    @Bean
    @ConditionalOnMissingBean
    public HttpSessionIdResolver httpSessionIdResolver() {
        return new RestHttpSessionIdResolver();
    }

    /**
     * 用户管理，用于踢出用户，后续考虑自己实现
     */
    @Bean
    @ConditionalOnMissingBean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthService authService() {
        return new SecurityAuthServiceImpl();
    }
}

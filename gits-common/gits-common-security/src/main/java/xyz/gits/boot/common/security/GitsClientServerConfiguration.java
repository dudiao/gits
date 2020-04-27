package xyz.gits.boot.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.session.web.http.HttpSessionIdResolver;
import xyz.gits.boot.common.security.filter.GitsLogoutFilter;
import xyz.gits.boot.common.security.filter.GitsUsernamePasswordAuthenticationFilter;
import xyz.gits.boot.common.security.hander.AnonymousAuthenticationEntryPoint;
import xyz.gits.boot.common.security.hander.InvalidSessionHandler;
import xyz.gits.boot.common.security.hander.LoginUserAccessDeniedHandler;
import xyz.gits.boot.common.security.hander.SessionInformationExpiredHandler;

/**
 * @author songyinyin
 * @date 2020/4/27 上午 12:42
 */
@Configuration
public class GitsClientServerConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * 未登录的处理
     */
    @Autowired
    private AnonymousAuthenticationEntryPoint anonymousAuthenticationEntryPoint;
    /**
     * 超时处理
     */
    @Autowired
    private InvalidSessionHandler invalidSessionHandler;
    /**
     * 顶号处理
     */
    @Autowired
    private SessionInformationExpiredHandler sessionInformationExpiredHandler;
    /**
     * 登录用户没有权限访问资源
     */
    @Autowired
    private LoginUserAccessDeniedHandler loginUserAccessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.authorizeRequests()
                // 放行接口
                .antMatchers(AUTH_WHITELIST).permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                // 异常处理(权限拒绝、登录失效等)
                .and().exceptionHandling()
                .authenticationEntryPoint(anonymousAuthenticationEntryPoint)//匿名用户访问无权限资源时的异常处理
                .accessDeniedHandler(loginUserAccessDeniedHandler)//登录用户没有权限访问资源
                // 会话管理
                .and().sessionManagement().invalidSessionStrategy(invalidSessionHandler) // 超时处理
                .maximumSessions(1)//同一账号同时登录最大用户数
                .expiredSessionStrategy(sessionInformationExpiredHandler) // 顶号处理
        ;
        // 资源服务器，需要跳过登录登出filter
        http.addFilter(new GitsUsernamePasswordAuthenticationFilter());
        http.addFilter(new GitsLogoutFilter());
    }

    private static final String[] AUTH_WHITELIST = {
            "/open/**",
            "/assets/**",
            "/instances",
            "/actuator/**",
            "/favicon.ico",

            // swagger 相关
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger-resources/**",
            "/v2/api-docs"
    };

}

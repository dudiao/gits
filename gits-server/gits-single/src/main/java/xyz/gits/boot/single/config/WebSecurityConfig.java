package xyz.gits.boot.single.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import xyz.gits.boot.common.core.config.GitsProperties;
import xyz.gits.boot.common.security.GitsResourceServerConfiguration;
import xyz.gits.boot.common.security.RestHttpSessionIdResolver;
import xyz.gits.boot.common.security.hander.AnonymousAuthenticationEntryPoint;
import xyz.gits.boot.common.security.hander.InvalidSessionHandler;
import xyz.gits.boot.common.security.hander.LoginUserAccessDeniedHandler;
import xyz.gits.boot.common.security.hander.SessionInformationExpiredHandler;
import xyz.gits.boot.security.login.extend.ExtendAuthenticationSecurityConfig;
import xyz.gits.boot.security.login.handler.LoginFailureHandler;
import xyz.gits.boot.security.login.handler.LoginSuccessHandler;
import xyz.gits.boot.security.login.handler.LogoutSuccessHandler;
import xyz.gits.boot.security.login.service.DefaultUserDetailsServiceImpl;
import xyz.gits.boot.security.login.verifycode.VerifyCodeFilter;

/**
 * @author songyinyin
 * @date 2020/2/19 下午 10:16
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DefaultUserDetailsServiceImpl userDetailsService;
    /**
     * 登出成功的处理
     */
    @Autowired
    private LoginFailureHandler loginFailureHandler;
    /**
     * 登录成功的处理
     */
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;
    /**
     * 登出成功的处理
     */
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;
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
    private LoginUserAccessDeniedHandler accessDeniedHandler;
    /**
     * 扩展用户登录
     */
    @Autowired
    private ExtendAuthenticationSecurityConfig extendAuthenticationSecurityConfig;
    /**
     * 验证码过滤器
     */
    @Lazy
    @Autowired
    private VerifyCodeFilter verifyCodeFilter;

    @Autowired
    private GitsProperties properties;

    /**
     * 配置认证方式等
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    /**
     * http相关的配置，包括登入登出、异常处理、会话管理等
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        // 扩展用户登录
        http.apply(extendAuthenticationSecurityConfig)
            .and().authorizeRequests()
            // 放行接口
            .antMatchers(GitsResourceServerConfiguration.AUTH_WHITELIST).permitAll()
            // 除上面外的所有请求全部需要鉴权认证
            .anyRequest().authenticated()
            // 异常处理(权限拒绝、登录失效等)
            .and().exceptionHandling()
            // 匿名用户访问无权限资源时的异常处理
            .authenticationEntryPoint(anonymousAuthenticationEntryPoint)
            // 登录用户没有权限访问资源
            .accessDeniedHandler(accessDeniedHandler)
            // ==========登入===========
            .and().formLogin()
            // 允许所有用户
            .permitAll()
            // 登录成功处理逻辑
            .successHandler(loginSuccessHandler)
            // 登录失败处理逻辑
            .failureHandler(loginFailureHandler)
            // ==========登出===========
            .and().logout()
            // 允许所有用户
            .permitAll()
            // 登出成功处理逻辑
            .logoutSuccessHandler(logoutSuccessHandler)
            .deleteCookies(RestHttpSessionIdResolver.AUTH_TOKEN)
            // ==========会话管理=========
            .and().sessionManagement()
            // 超时处理
            .invalidSessionStrategy(invalidSessionHandler)
            // 同一账号同时登录最大用户数
            .maximumSessions(1)
            // 顶号处理
            .expiredSessionStrategy(sessionInformationExpiredHandler)
        ;

        // 验证码过滤器
        if (properties.getSecurity().isVerifyCodeEnable()) {
            http.addFilterBefore(verifyCodeFilter, UsernamePasswordAuthenticationFilter.class);
        }
    }

    /**
     * 重写 spring session redis 序列化
     */
    /*@Bean("springSessionDefaultRedisSerializer")
    public RedisSerializer<Object> redisSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }*/

}

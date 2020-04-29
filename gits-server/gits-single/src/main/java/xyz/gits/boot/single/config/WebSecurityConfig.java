package xyz.gits.boot.single.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.session.web.http.HttpSessionIdResolver;
import xyz.gits.boot.common.security.RestHttpSessionIdResolver;
import xyz.gits.boot.common.security.hander.AnonymousAuthenticationEntryPoint;
import xyz.gits.boot.common.security.hander.InvalidSessionHandler;
import xyz.gits.boot.common.security.hander.LoginUserAccessDeniedHandler;
import xyz.gits.boot.common.security.hander.SessionInformationExpiredHandler;
import xyz.gits.boot.single.auth.LoginFailureHandler;
import xyz.gits.boot.single.auth.LoginSuccessHandler;
import xyz.gits.boot.single.auth.LogoutSuccessHandler;

/**
 * @author songyinyin
 * @date 2020/2/19 下午 10:16
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
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
        http.authorizeRequests()
                // 放行接口
                .antMatchers(AUTH_WHITELIST).permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                // 异常处理(权限拒绝、登录失效等)
                .and().exceptionHandling()
                .authenticationEntryPoint(anonymousAuthenticationEntryPoint)//匿名用户访问无权限资源时的异常处理
                .accessDeniedHandler(accessDeniedHandler)//登录用户没有权限访问资源
                // 登入
                .and().formLogin().permitAll()//允许所有用户
                .successHandler(loginSuccessHandler)//登录成功处理逻辑
                .failureHandler(loginFailureHandler)//登录失败处理逻辑
                // 登出
                .and().logout().permitAll()//允许所有用户
                .logoutSuccessHandler(logoutSuccessHandler)//登出成功处理逻辑
                .deleteCookies("SESSION")
                // 会话管理
                .and().sessionManagement().invalidSessionStrategy(invalidSessionHandler) // 超时处理
                .maximumSessions(1)//同一账号同时登录最大用户数
                .expiredSessionStrategy(sessionInformationExpiredHandler) // 顶号处理
        ;

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

    /**
     * 重写 spring session redis 序列化
     */
    /*@Bean("springSessionDefaultRedisSerializer")
    public RedisSerializer<Object> redisSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }*/

    @Bean
    public AnonymousAuthenticationEntryPoint anonymousAuthenticationEntryPoint() {
        return new AnonymousAuthenticationEntryPoint();
    }

    @Bean
    public InvalidSessionHandler invalidSessionHandler() {
        return new InvalidSessionHandler();
    }

    @Bean
    public SessionInformationExpiredHandler sessionInformationExpiredHandler() {
        return new SessionInformationExpiredHandler();
    }

    @Bean
    public LoginUserAccessDeniedHandler loginUserAccessDeniedHandler() {
        return new LoginUserAccessDeniedHandler();
    }

    /**
     * 自定义session id 存放路径，支持cookie，header 和 request parameter
     */
    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
        return new RestHttpSessionIdResolver();
    }

    /**
     * 用户管理，用于踢出用户，后续考虑自己实现
     */
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }


}

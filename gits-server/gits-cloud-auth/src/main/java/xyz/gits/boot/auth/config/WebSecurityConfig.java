package xyz.gits.boot.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import xyz.gits.boot.auth.handler.LoginFailureHandler;
import xyz.gits.boot.auth.handler.LoginSuccessHandler;
import xyz.gits.boot.auth.handler.LogoutSuccessHandler;
import xyz.gits.boot.common.security.PermissionService;
import xyz.gits.boot.common.security.hander.AnonymousAuthenticationEntryPoint;
import xyz.gits.boot.common.security.hander.InvalidSessionHandler;
import xyz.gits.boot.common.security.hander.LoginUserAccessDeniedHandler;
import xyz.gits.boot.common.security.hander.SessionInformationExpiredHandler;

/**
 * 'prePostEnabled = true' --> 使{@link PreAuthorize PostAuthorize} {@link PostAuthorize}和{@link PermissionService}生效 <br/>
 * <p>
 * 'securedEnabled = true' --> 使{@link Secured}生效
 *
 * @author songyinyin
 * @date 2020/2/19 下午 10:16
 */
@Configuration
@EnableWebSecurity
@EnableRedisHttpSession
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


}

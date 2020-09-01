package xyz.gits.boot.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import xyz.gits.boot.common.security.hander.AnonymousAuthenticationEntryPoint;
import xyz.gits.boot.common.security.hander.InvalidSessionHandler;
import xyz.gits.boot.common.security.hander.LoginUserAccessDeniedHandler;
import xyz.gits.boot.common.security.hander.SessionInformationExpiredHandler;

/**
 * 使用 @EnableGitsResourceServer 注解才能装配此类
 *
 * @author songyinyin
 * @date 2020/4/27 上午 12:42
 */
@Configuration
public class GitsResourceServerConfiguration extends WebSecurityConfigurerAdapter {

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
    /**
     * 放行URL
     */
    @Autowired
    private PermitAllUrlProperties permitAllUrlProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.authorizeRequests()
            // 放行接口
            .antMatchers(permitAllUrlProperties.getIgnoreUrls().toArray(new String[0])).permitAll()
            // 除上面外的所有请求全部需要鉴权认证
            .anyRequest().authenticated()
            // 异常处理(权限拒绝、登录失效等)
            .and().exceptionHandling()
            // 匿名用户访问无权限资源时的异常处理
            .authenticationEntryPoint(anonymousAuthenticationEntryPoint)
            // 登录用户没有权限访问资源
            .accessDeniedHandler(loginUserAccessDeniedHandler)
            // 会话管理
            .and().sessionManagement()
            // 超时处理
            .invalidSessionStrategy(invalidSessionHandler)
            // 同一账号同时登录最大用户数
            .maximumSessions(1)
            // 顶号处理
            .expiredSessionStrategy(sessionInformationExpiredHandler)
        ;
        // 资源服务器，需要跳过登录登出filter
        http.formLogin().disable().logout().disable();
    }

}

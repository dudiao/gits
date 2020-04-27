package xyz.gits.boot.common.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HttpSessionIdResolver;
import xyz.gits.boot.common.security.hander.AnonymousAuthenticationEntryPoint;
import xyz.gits.boot.common.security.hander.InvalidSessionHandler;
import xyz.gits.boot.common.security.hander.LoginUserAccessDeniedHandler;
import xyz.gits.boot.common.security.hander.SessionInformationExpiredHandler;

/**
 * @author songyinyin
 * @date 2020/4/27 下午 03:57
 */
@Configuration
@EnableWebSecurity
@EnableRedisHttpSession
public class GitsSecurityAutoConfiguration {

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
}

package xyz.gits.boot.security.login;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import xyz.gits.boot.security.login.extend.ExtendLoginController;
import xyz.gits.boot.security.login.extend.ExtendUserDetailsService;
import xyz.gits.boot.security.login.handler.LoginFailureHandler;
import xyz.gits.boot.security.login.handler.LoginSuccessHandler;
import xyz.gits.boot.security.login.handler.LogoutSuccessHandler;
import xyz.gits.boot.security.login.service.ExtendUserDetailsServiceImpl;
import xyz.gits.boot.security.login.service.DefaultUserDetailsService;

@Configuration
public class GitsSecurityLoginAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new LogoutSuccessHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultUserDetailsService defaultUserDetailsService() {
        return new DefaultUserDetailsService();
    }

    @Bean
    @ConditionalOnMissingBean
    public ExtendUserDetailsService extendUserDetailsService() {
        return new ExtendUserDetailsServiceImpl();
    }

    @Bean
    @Lazy
    @ConditionalOnMissingBean
    public ExtendLoginController extendLoginController() {
        return new ExtendLoginController();
    }

}

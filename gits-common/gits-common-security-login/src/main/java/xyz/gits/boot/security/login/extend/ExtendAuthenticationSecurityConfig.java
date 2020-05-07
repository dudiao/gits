package xyz.gits.boot.security.login.extend;

import com.xkcoding.justauth.AuthRequestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 扩展第三方登录配置
 *
 * @author songyinyin
 * @date 2020/5/4 下午 07:58
 */
@Configuration
public class ExtendAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private AuthRequestFactory authRequestFactory;
    @Autowired
    private ExtendUserDetailsService extendUserDetailsService;
    @Autowired
    private AuthenticationSuccessHandler successHandler;
    @Autowired
    private AuthenticationFailureHandler failureHandler;

    @Override
    public void configure(HttpSecurity builder) throws Exception {

        // 1. 初始化 ExtendAuthenticationFilter
        ExtendAuthenticationFilter filter = new ExtendAuthenticationFilter();
        filter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationFailureHandler(failureHandler);
        filter.setAuthRequestFactory(authRequestFactory);

        // 2. 初始化 ExtendAuthenticationProvider
        ExtendAuthenticationProvider provider = new ExtendAuthenticationProvider();
        provider.setExtendUserDetailsService(extendUserDetailsService);

        // 3. 将设置完毕的 Filter 与 Provider 添加到配置中，将自定义的 Filter 加到 UsernamePasswordAuthenticationFilter 之前
        builder.authenticationProvider(provider).addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }
}

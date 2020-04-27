package xyz.gits.boot.common.security.filter;

import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessEventPublishingLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author songyinyin
 * @date 2020/4/27 上午 11:01
 */
public class GitsLogoutFilter extends LogoutFilter {

    public GitsLogoutFilter() {
        super("/logout", new LogoutSuccessEventPublishingLogoutHandler());
    }

    public GitsLogoutFilter(LogoutSuccessHandler logoutSuccessHandler, LogoutHandler... handlers) {
        super(logoutSuccessHandler, handlers);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 资源服务器，需要跳过登出接口
        chain.doFilter(request, response);
    }

}

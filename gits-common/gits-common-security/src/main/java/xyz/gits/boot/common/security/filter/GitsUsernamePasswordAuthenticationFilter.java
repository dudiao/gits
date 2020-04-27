package xyz.gits.boot.common.security.filter;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author songyinyin
 * @date 2020/4/27 上午 01:43
 */
public class GitsUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    public GitsUsernamePasswordAuthenticationFilter() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 资源服务器，需要跳过登录接口
        chain.doFilter(request, response);
    }

}

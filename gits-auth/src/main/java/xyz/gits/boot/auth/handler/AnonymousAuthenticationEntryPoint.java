package xyz.gits.boot.auth.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import xyz.gits.boot.common.response.ResponseCode;
import xyz.gits.boot.common.response.RestResponse;
import xyz.gits.boot.common.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 未登录时访问无权限资源时的异常
 *
 * @author songyinyin
 * @date 2020/2/25 下午 08:53
 */
@Slf4j
@Component
public class AnonymousAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.info("用户需要登录，访问[{}]失败", request.getRequestURI());

        ServletUtils.render(request, response, RestResponse.fail(ResponseCode.USER_NEED_LOGIN));
    }
}

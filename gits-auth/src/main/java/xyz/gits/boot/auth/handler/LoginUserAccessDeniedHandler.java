package xyz.gits.boot.auth.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import xyz.gits.boot.common.response.ResponseCode;
import xyz.gits.boot.common.response.RestResponse;
import xyz.gits.boot.common.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录用户没有权限访问资源
 *
 * @author songyinyin
 * @date 2020/3/18 下午 09:08
 */
@Slf4j
@Component
public class LoginUserAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ServletUtils.render(request, response, RestResponse.build(ResponseCode.NO_AUTHENTICATION));
    }
}

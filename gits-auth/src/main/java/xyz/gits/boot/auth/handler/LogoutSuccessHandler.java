package xyz.gits.boot.auth.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import xyz.gits.boot.common.response.RestResponse;
import xyz.gits.boot.common.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 注销成功回调
 *
 * @author songyinyin
 * @date 2020/2/25 下午 09:28
 */
@Slf4j
@Component
public class LogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // TODO 登出成功 记录登出日志
        ServletUtils.render(request, response, RestResponse.success());
    }
}

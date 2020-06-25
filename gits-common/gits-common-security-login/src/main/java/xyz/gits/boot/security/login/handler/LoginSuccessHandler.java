package xyz.gits.boot.security.login.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import xyz.gits.boot.common.core.enums.LoginType;
import xyz.gits.boot.common.core.response.RestResponse;
import xyz.gits.boot.common.core.utils.ServletUtils;
import xyz.gits.boot.common.security.SecurityLoginUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功处理逻辑
 *
 * @author songyinyin
 * @date 2020/2/25 下午 09:05
 */
@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // TODO 登录成功 记录日志，需要区分密码登录还是第三方登录
        SecurityLoginUser securityLoginUser = (SecurityLoginUser) authentication.getPrincipal();
        // 不是密码登陆，需要重定向到前端首页
        if (!LoginType.PASSWORD.equals(securityLoginUser.getLoginUser().getLoginType())) {
            log.info("第三方登陆，重定向到首页");
            response.sendRedirect("/web/index");
            return;
        }
        ServletUtils.render(request, response, RestResponse.success(authentication));
    }
}

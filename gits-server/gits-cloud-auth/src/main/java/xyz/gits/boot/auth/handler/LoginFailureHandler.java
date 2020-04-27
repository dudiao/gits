package xyz.gits.boot.auth.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import xyz.gits.boot.common.core.response.ResponseCode;
import xyz.gits.boot.common.core.response.RestResponse;
import xyz.gits.boot.common.core.utils.ServletUtils;
import xyz.gits.boot.common.security.UserUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败的回调
 *
 * @author songyinyin
 * @date 2020/2/25 下午 09:20
 */
@Slf4j
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        RestResponse result;
        String username = UserUtil.loginUsername(request);
        if (e instanceof AccountExpiredException) {
            // 账号过期
            log.debug("[登录失败] - 用户[{}]账号过期", username);
            result = RestResponse.build(ResponseCode.USER_ACCOUNT_EXPIRED);

        } else if (e instanceof BadCredentialsException) {
            // 密码错误
            log.debug("[登录失败] - 用户[{}]密码错误", username);
            result = RestResponse.build(ResponseCode.USER_PASSWORD_ERROR);

        } else if (e instanceof CredentialsExpiredException) {
            // 密码过期
            log.debug("[登录失败] - 用户[{}]密码过期", username);
            result = RestResponse.build(ResponseCode.USER_PASSWORD_EXPIRED);

        } else if (e instanceof DisabledException) {
            // 用户被禁用
            log.debug("[登录失败] - 用户[{}]被禁用", username);
            result = RestResponse.build(ResponseCode.USER_DISABLED);

        } else if (e instanceof LockedException) {
            // 用户被锁定
            log.debug("[登录失败] - 用户[{}]被锁定", username);
            result = RestResponse.build(ResponseCode.USER_LOCKED);

        } else if (e instanceof InternalAuthenticationServiceException) {
            // 内部错误
            log.error(String.format("[登录失败] - [%s]内部错误", username), e);
            result = RestResponse.fail();

        } else {
            // 其他错误
            log.error(String.format("[登录失败] - [%s]其他错误", username), e);
            result = RestResponse.fail();
        }
        ServletUtils.render(request, response, result);
    }
}

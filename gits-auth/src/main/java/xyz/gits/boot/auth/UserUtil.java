package xyz.gits.boot.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import xyz.gits.boot.common.exception.SystemException;
import xyz.gits.boot.common.response.ResponseCode;

import javax.servlet.http.HttpServletRequest;

/**
 * @author songyinyin
 * @date 2020/3/14 下午 05:16
 */
@Slf4j
public class UserUtil {

    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";

    public static LoginUser loginUser() {
        try {
            return (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new SystemException(ResponseCode.CURRENT_USER_FAIL, e);
        }
    }

    public static String loginUsername(HttpServletRequest request) {
        return request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY);
    }
}

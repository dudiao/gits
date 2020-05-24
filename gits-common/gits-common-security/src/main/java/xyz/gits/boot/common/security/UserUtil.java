package xyz.gits.boot.common.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import xyz.gits.boot.common.core.exception.SystemException;
import xyz.gits.boot.common.core.response.ResponseCode;

import javax.servlet.http.HttpServletRequest;

/**
 * @author songyinyin
 * @date 2020/3/14 下午 05:16
 */
@Slf4j
public class UserUtil {

    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";

    /**
     * 扩展类型
     */
    public static String EXTEND_TYPE_PARAMETER = "extendType";
    /**
     * 用户登录扩展key，如手机号
     */
    public static String EXTEND_KEY_PARAMETER = "extendKey";
    /**
     * 用户登录扩展凭证，如手机号的验证码
     */
    public static String EXTEND_CREDENTIALS_PARAMETER = "extendCredentials";

    /**
     * 获取当前登录用户
     */
    public static LoginUser loginUser() {
        try {
            return (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new SystemException(ResponseCode.CURRENT_USER_FAIL, e);
        }
    }

    /**
     * 获取当前登录用户id
     */
    public static String getUserId() {
        return loginUser().getUser().getUserId();
    }

    /**
     * 获取当前登录用户名称
     */
    public static String getUserName() {
        return loginUser().getUser().getUserName();
    }

    /**
     * 从登录请求中，获取登录用户的用户名
     */
    public static String loginUsername(HttpServletRequest request) {
        return request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY);
    }

    /**
     * 从登录请求中，获取扩展登录的 extendKey
     */
    public static String loginExtendKey(HttpServletRequest request) {
        return request.getParameter(EXTEND_KEY_PARAMETER);
    }

    /**
     * 从登录请求中，获取扩展登录的 extendType
     */
    public static String loginExtendType(HttpServletRequest request) {
        return request.getParameter(EXTEND_TYPE_PARAMETER);
    }
}

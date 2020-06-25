package xyz.gits.boot.api.system.utils;

import lombok.extern.slf4j.Slf4j;
import xyz.gits.boot.api.system.service.AuthService;
import xyz.gits.boot.common.core.utils.SpringContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * @author songyinyin
 * @date 2020/3/14 下午 05:16
 */
@Slf4j
public class UserUtil {

    private static AuthService authService = SpringContextHolder.getBean(AuthService.class);

    /**
     * 获取当前登录用户
     */
    public static Object loginUser() {
        return authService.loginUser();
    }

    /**
     * 获取当前登录用户id
     */
    public static String getUserId() {
        return authService.loginUser().getUser().getUserId();
    }

    /**
     * 获取当前登录用户名称
     */
    public static String getUserName() {
        return authService.loginUser().getUser().getUserName();
    }

    /**
     * 获取当前登录用户机构id
     */
    public static String getUserOrgId() {
        return authService.loginUser().getUser().getOrgId();
    }

    /**
     * 获取当前登录用户角色集合
     */
    public static Set<String> getUserRoles() {
        return authService.loginUser().getRoles();
    }

    /**
     * 获取当前登录用户权限集合
     */
    public static Set<String> getUserPermissions() {
        return authService.loginUser().getPermissions();
    }

    /**
     * 从登录请求中，获取登录用户的用户名
     */
    public static String loginUsername(HttpServletRequest request) {
        return authService.loginUsername(request);
    }

    /**
     * 从登录请求中，获取扩展登录的 extendKey
     */
    public static String loginExtendKey(HttpServletRequest request) {
        return authService.loginExtendKey(request);
    }

    /**
     * 用户登录扩展凭证，如手机号的验证码
     */
    public static String loginExtendCredentials(HttpServletRequest request) {
        return authService.loginExtendCredentials(request);
    }

    /**
     * 从登录请求中，获取扩展登录的 extendType
     */
    public static String loginExtendType(HttpServletRequest request) {
        return authService.loginExtendType(request);
    }


}

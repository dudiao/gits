package xyz.gits.boot.common.security;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author songyinyin
 * @date 2020/3/14 下午 05:16
 */
@Slf4j
public class SecurityConstant {

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
}

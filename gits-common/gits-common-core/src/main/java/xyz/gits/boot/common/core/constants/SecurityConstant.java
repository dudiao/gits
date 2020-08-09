package xyz.gits.boot.common.core.constants;

/**
 *
 * @author songyinyin
 * @date 2020/3/14 下午 05:16
 */
public interface SecurityConstant {

    long SERIAL_VERSION_UID = 520;

    String SPRING_SECURITY_FORM_USERNAME_KEY = "username";

    /**
     * 扩展类型
     */
    String EXTEND_TYPE_PARAMETER = "extendType";
    /**
     * 用户登录扩展key，如手机号
     */
    String EXTEND_KEY_PARAMETER = "extendKey";
    /**
     * 用户登录扩展凭证，如手机号的验证码
     */
    String EXTEND_CREDENTIALS_PARAMETER = "extendCredentials";
    /**
     * 请求来源
     */
    String FROM = "from";
    /**
     * 请求来源：内部
     */
    String FROM_IN = "in";
}

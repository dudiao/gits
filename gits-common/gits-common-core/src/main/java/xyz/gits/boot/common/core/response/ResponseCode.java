package xyz.gits.boot.common.core.response;

/**
 * @author songyinyin
 * @date 2020/1/17 16:12
 */
public enum ResponseCode {
    /**
     * 公共返回码
     */
    FAIL(-1, "系统繁忙"),
    SUCCESS(0, "请求成功"),

    TOO_MANY_REQUESTS(429, "Too Many Requests"),

    GLOBAL_PARAM_ERROR(4000, "参数错误"),
    /**
     * 获取当前用户失败
     */
    CURRENT_USER_FAIL(10001, "获取当前用户失败"),

    // 登录
    USER_NEED_LOGIN(11001,"用户未登录，请登陆后进行访问"),
    USER_MAX_LOGIN(11002,"该用户已在其它地方登录"),
    USER_LOGIN_TIMEOUT(11003,"长时间未操作，自动退出"),
    USER_DISABLED(11004,"用户被禁用"),
    USER_LOCKED(11005,"用户被锁定"),
    USER_PASSWORD_ERROR(11006,"用户名或密码错误"),
    USER_PASSWORD_EXPIRED(11007,"用户密码过期"),
    USER_ACCOUNT_EXPIRED(11008,"用户账号过期"),
    USER_NOT_EXIST(11009,"用户不存在"),
    USER_LOGIN_FAIL(11010,"用户登录失败"),
    VERIFY_CODE_ERROR(11011,"验证码错误"),
    NO_AUTHENTICATION(11012,"无权访问"),

    // 资源
    RESOURCE_INVALID(12001, "无效的资源ID"),
    RESOURCE_PARENT_INVALID(12002, "无效资源父节点ID"),
    RESOURCE_HAVE_SUB(12003, "该资源下有子资源，不能删除"),
    // 机构管理
    ORG_NOT_EXIST(17002, "机构不存在"),
    ORG_HAVE_USER(17003, "机构下存在用户"),
    ORG_HAVE_SUB(17004, "机构存在子机构"),

    // 文件系统
    FILE_DOES_NOT_EXIST(16001,"文件不存在"),
    FILE_UPLOAD_EXCEPTION(16002,"文件上传异常"),
    FILE_DOWNLOAD_ABNORMAL(16003,"文件下载异常"),
    ;


    private int code;
    private String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    }

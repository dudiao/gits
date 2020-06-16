package xyz.gits.boot.common.core.constants;


/**
 * 缓存key
 *
 * @author songyinyin
 * @date 2020/5/30 下午 11:40
 */
public interface CacheConstants {

    /**
     * 缓存前缀
     */
    String CACHE_PREFIX = "gits:";

    /**
     * 验证码
     */
    String VALIDATE_CODE = CACHE_PREFIX + "validate_code:";

    /**
     * 机构树
     */
    String ORG_TREE = CACHE_PREFIX + "org_tree";

    /**
     * 角色资源
     */
    String ROLE_RESOURCE = CACHE_PREFIX + "role_resource:";

    /**
     * 登录用户
     */
    String LOGIN_USER = CACHE_PREFIX + "login_user:";

}

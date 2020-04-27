package xyz.gits.boot.common.core.constants;

/**
 * @author songyinyin
 * @date 2020/3/14 下午 08:16
 */
public interface SystemConstants {

    /**
     * 密码锁定标志（0-未锁定，1-密码错误锁定）
     */
    String PWD_UNLOCK = "0";
    /**
     * 密码锁定标志（0-未锁定，1-密码错误锁定）
     */
    String PWD_LOCK = "1";

    /**
     * 停用标志（0:停用，1:启用）
     */
    String USER_ENABLE = "0";
    /**
     * 停用标志（0:停用，1:启用）
     */
    String USER_STOP = "1";

}

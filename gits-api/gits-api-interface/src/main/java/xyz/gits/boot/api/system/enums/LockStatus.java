package xyz.gits.boot.api.system.enums;

import xyz.gits.boot.common.core.enums.CodeEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 * 锁定状态[0:未锁定;1:已锁定]
 *
 * @author null
 * @date 2020/06/04/11:20
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum LockStatus implements CodeEnum {

    /**
     * 未锁定
     */
    UN_LOCK("0", "未锁定"),

    /**
     * 已锁定
     */
    LOCK("1", "已锁定");

    @EnumValue
    private String code;
    private String message;

    LockStatus() {
    }

    LockStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * code转枚举类型
     *
     * @param code code码
     * @return {@link LockStatus}
     * @author null
     * @date 2020/6/5 11:36
     */
    public static LockStatus fromString(String code) {
        for (LockStatus b : LockStatus.values()) {
            if (b.code.equalsIgnoreCase(code)) {
                return b;
            }
        }
        return null;
    }
}
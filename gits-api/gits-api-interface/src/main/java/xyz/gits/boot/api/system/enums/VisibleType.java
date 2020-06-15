package xyz.gits.boot.api.system.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import xyz.gits.boot.common.core.enums.CodeEnum;

import java.util.Arrays;

/**
 * 显示类型
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum VisibleType implements CodeEnum {

    /**
     * 显示
     */
    SHOW("0", "显示"),

    /**
     * 隐藏
     */
    HIDE("1", "隐藏");

    @EnumValue
    private String code;

    private String message;

    VisibleType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * code转枚举类型
     *
     * @param code code码
     * @return {@link VisibleType}
     */
    public static VisibleType fromString(String code) {
        for (VisibleType b : VisibleType.values()) {
            if (b.code.equalsIgnoreCase(code)) {
                return b;
            }
        }
        throw new IllegalArgumentException("请传入枚举类型：" + Arrays.toString(VisibleType.values()));
    }
}
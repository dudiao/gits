package xyz.gits.boot.api.system.enums;

import xyz.gits.boot.common.core.enums.CodeEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 * 是否[0:否;1是]
 *
 * @author null
 * @date 2020/06/04/11:42
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Whether implements CodeEnum {

    NO("0", "否"),

    YES("1", "是");
    @EnumValue
    private String code;

    private String message;

    Whether() {
    }

    Whether(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * code转枚举类型
     *
     * @param code code码
     * @return {@link Whether}
     * @author null
     * @date 2020/6/5 11:36
     */
    public static Whether fromString(String code) {
        for (Whether b : Whether.values()) {
            if (b.code.equalsIgnoreCase(code)) {
                return b;
            }
        }
        return null;
    }
}

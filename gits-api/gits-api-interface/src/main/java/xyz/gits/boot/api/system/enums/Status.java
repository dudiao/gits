package xyz.gits.boot.api.system.enums;

import xyz.gits.boot.common.core.enums.CodeEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 * 有效状态[0:有效;1:无效]
 *
 * @author null
 * @date 2020/06/04/11:02
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Status implements CodeEnum {

    VALID("0", "有效"),
    INVALID("1", "无效");
    @EnumValue
    private String code;
    private String message;

    Status() {
    }

    Status(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * code转枚举类型
     *
     * @param code code码
     * @return {@link Status}
     * @author null
     * @date 2020/6/5 11:36
     */
    public static Status fromString(String code) {
        for (Status b : Status.values()) {
            if (b.code.equalsIgnoreCase(code)) {
                return b;
            }
        }
        return null;
    }
}

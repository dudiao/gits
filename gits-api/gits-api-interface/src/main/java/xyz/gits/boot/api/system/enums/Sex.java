package xyz.gits.boot.api.system.enums;

import xyz.gits.boot.common.core.enums.CodeEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 * 性别[0:女;1:男]
 *
 * @author null
 * @date 2020/06/04/16:30
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Sex implements CodeEnum {
    WOMAN("0", "女"),
    MAN("1", "男");
    @EnumValue
    private String code;
    private String message;

    Sex() {
    }

    Sex(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * code转枚举类型
     *
     * @param code code码
     * @return {@link Sex}
     * @author null
     * @date 2020/6/5 11:36
     */
    public static Sex fromString(String code) {
        for (Sex b : Sex.values()) {
            if (b.code.equalsIgnoreCase(code)) {
                return b;
            }
        }
        return null;
    }
}

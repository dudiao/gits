package xyz.gits.boot.api.system.enums;

import xyz.gits.boot.common.core.enums.CodeEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author null
 * @date 2020/07/08/16:58
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum LoginType implements CodeEnum {

    PASSWORD("0", "密码登录"),
    EXTEND("1", "三方登录"),

    ;

    @EnumValue
    private String code;

    private String message;

    LoginType() {
    }

    LoginType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * code转枚举类型
     *
     * @param code code码
     * @return {@link ResourceType}
     * @author null
     * @date 2020/6/5 11:36
     */
    public static LoginType fromString(String code) {
        for (LoginType b : LoginType.values()) {
            if (b.code.equalsIgnoreCase(code)) {
                return b;
            }
        }
        throw new IllegalArgumentException("请传入枚举类型：" + Arrays.toString(ResourceType.values()));
    }
}

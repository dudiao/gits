package xyz.gits.boot.api.system.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import xyz.gits.boot.common.core.enums.CodeEnum;

import java.util.Arrays;

/**
 * @author dingmingyang
 * @date 2020/07/08/16:58
 */
@Slf4j
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum LoginType implements CodeEnum {

    /**
     * 密码登录
     */
    PASSWORD("0", "密码登录"),
    /**
     * 三方登录
     */
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
     * 反序列化时的初始化函数
     */
    @JsonCreator
    public static LoginType getItem(@JsonProperty("code") String code) {
        for (LoginType item : values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        log.warn("[LoginType] 枚举反序列化异常：code={}", code);
        throw new IllegalArgumentException("请传入枚举类型：" + Arrays.toString(LoginType.values()));
    }

    /**
     * code转枚举类型
     *
     * @param code code码
     * @return {@link LoginType}
     * @author dingmingyang
     * @date 2020/6/5 11:36
     */
    public static LoginType fromString(String code) {
        for (LoginType b : LoginType.values()) {
            if (b.code.equalsIgnoreCase(code)) {
                return b;
            }
        }
        log.warn("[LoginType] 无此枚举：code={}", code);
        throw new IllegalArgumentException("请传入枚举类型：" + Arrays.toString(LoginType.values()));
    }
}

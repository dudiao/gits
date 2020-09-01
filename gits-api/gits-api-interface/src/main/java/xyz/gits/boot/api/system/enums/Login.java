package xyz.gits.boot.api.system.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import xyz.gits.boot.common.core.enums.CodeEnum;

import java.util.Arrays;


/**
 * 登录结果[0:登录失败;1:登录成功;2:登出成功]
 *
 * @author zhangyapu
 * @date 2020/5/27 10:53
 * @since 1.0
 */
@Slf4j
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Login implements CodeEnum {

    /**
     * 登录成功
     */
    SUCCESS("1", "登录成功"),
    /**
     * 登录失败
     */
    FAIL("0", "登录失败"),

    /**
     * 登出成功
     */
    OUT_SUCCESS("2", "登出成功");

    @EnumValue
    private String code;
    @JsonValue
    private String message;

    Login() {
    }

    Login(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 反序列化时的初始化函数
     */
    @JsonCreator
    public static Login getItem(@JsonProperty("code") String code) {
        for (Login item : values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        log.warn("[Login] 枚举反序列化异常：code={}", code);
        throw new IllegalArgumentException("请传入枚举类型：" + Arrays.toString(Login.values()));
    }

    /**
     * code转枚举类型
     *
     * @param code code码
     * @return {@link Login}
     * @date 2020/6/5 11:36
     */
    public static Login fromString(String code) {
        for (Login b : Login.values()) {
            if (b.code.equalsIgnoreCase(code)) {
                return b;
            }
        }
        log.warn("[Login] 无此枚举：code={}", code);
        throw new IllegalArgumentException("请传入枚举类型：" + Arrays.toString(Login.values()));
    }

}

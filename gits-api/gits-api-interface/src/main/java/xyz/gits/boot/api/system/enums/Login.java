package xyz.gits.boot.api.system.enums;

import xyz.gits.boot.common.core.enums.CodeEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;


/**
 * 登录结果[1:登录成功;0:登录失败]
 *
 * @author zhangyapu
 * @date 2020/5/27 10:53
 * @since 1.0
 */
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
    FAIL("0", "登录失败");

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
}

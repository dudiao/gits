package xyz.gits.boot.api.system.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import xyz.gits.boot.common.core.enums.CodeEnum;

/**
 *显示类型type
 * @author luojie
 * @date 2019/11/19
 */
@Getter
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
    @JsonValue
    private String message;

    VisibleType(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
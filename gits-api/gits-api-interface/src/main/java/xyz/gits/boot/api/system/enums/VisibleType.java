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
    SHOW("1", "显示"),

    /**
     * 不显示
     */
    UN_SHOW("0", "不显示");


    @EnumValue
    private String code;
    @JsonValue
    private String message;

    VisibleType(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
package xyz.gits.boot.api.system.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import xyz.gits.boot.common.core.enums.CodeEnum;

/**
 * 资源类型
 *
 * @author songyinyin
 */
@Getter
public enum ResourceType implements CodeEnum {

    /**
     * 系统
     */
    A("A", "系统"),

    /**
     * 菜单
     */
    B("B", "菜单"),

    /**
     * 按钮
     */
    C("C", "按钮"),

    /**
     * 链接
     */
    D("D", "链接");

    @EnumValue
    private String code;
    @JsonValue
    private String message;

    ResourceType(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

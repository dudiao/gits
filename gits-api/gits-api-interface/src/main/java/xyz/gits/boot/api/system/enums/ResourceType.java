package xyz.gits.boot.api.system.enums;

import cn.hutool.core.util.EnumUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import xyz.gits.boot.common.core.enums.CodeEnum;

import java.util.Arrays;

/**
 * 资源类型
 *
 * @author songyinyin
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
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

    private String message;

    ResourceType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * code转枚举类型
     *
     * @param code code码
     * @return {@link ResourceType}
     */
    public static ResourceType fromString(String code) {
        for (ResourceType b : ResourceType.values()) {
            if (b.code.equalsIgnoreCase(code)) {
                return b;
            }
        }
        throw new IllegalArgumentException("请传入枚举类型：" + Arrays.toString(ResourceType.values()));
    }
}

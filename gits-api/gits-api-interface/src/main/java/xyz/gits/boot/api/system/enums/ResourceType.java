package xyz.gits.boot.api.system.enums;

import xyz.gits.boot.common.core.enums.CodeEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.Arrays;

/**
 * 资源类型[A:顶级目录;B:菜单;C:按钮;D:链接]
 *
 * @author songyinyin
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResourceType implements CodeEnum {

    /**
     * 顶级目录
     */
    A("A", "顶级目录"),

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

    ResourceType() {
    }

    ResourceType(String code, String message) {
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
    public static ResourceType fromString(String code) {
        for (ResourceType b : ResourceType.values()) {
            if (b.code.equalsIgnoreCase(code)) {
                return b;
            }
        }
        throw new IllegalArgumentException("请传入枚举类型：" + Arrays.toString(ResourceType.values()));
    }
}

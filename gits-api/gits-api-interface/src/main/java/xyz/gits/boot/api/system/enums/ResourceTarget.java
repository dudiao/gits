package xyz.gits.boot.api.system.enums;

import xyz.gits.boot.common.core.enums.CodeEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 * 菜单打开方式[menuItem:页签;menuBlank:弹窗;link:链接]
 *
 * @author null
 * @date 2020/5/12 15:07
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResourceTarget implements CodeEnum {

    /**
     * 页签
     */
    RESOURCE_ITEM("menuItem", "页签"),

    /**
     * 弹窗
     */
    RESOURCE_BLANK("menuBlank", "弹窗"),

    /**
     * 链接
     */
    LINK("link", "链接");
    @EnumValue
    private String type;

    private String name;

    ResourceTarget() {
    }

    ResourceTarget(String type, String name) {
        this.type = type;
        this.name = name;
    }

    /**
     * code转枚举类型
     *
     * @param type type码
     * @return {@link ResourceTarget}
     * @author null
     * @date 2020/6/5 11:36
     */
    public static ResourceTarget fromString(String type) {
        for (ResourceTarget b : ResourceTarget.values()) {
            if (b.type.equalsIgnoreCase(type)) {
                return b;
            }
        }
        return null;
    }

    @Override
    public String getCode() {
        return type;
    }
}

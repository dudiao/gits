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
 * 资源打开方式[menuItem:页签;menuBlank:弹窗;link:链接]
 *
 * @author dingmingyang
 * @date 2020/5/12 15:07
 */
@Slf4j
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
     * 反序列化时的初始化函数
     */
    @JsonCreator
    public static ResourceTarget getItem(@JsonProperty("code") String code) {
        for (ResourceTarget item : values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        log.warn("[ResourceTarget] 枚举反序列化异常：code={}", code);
        throw new IllegalArgumentException("请传入枚举类型：" + Arrays.toString(ResourceTarget.values()));
    }

    /**
     * code转枚举类型
     *
     * @param type type码
     * @return {@link ResourceTarget}
     * @author dingmingyang
     * @date 2020/6/5 11:36
     */
    public static ResourceTarget fromString(String type) {
        for (ResourceTarget b : ResourceTarget.values()) {
            if (b.type.equalsIgnoreCase(type)) {
                return b;
            }
        }
        log.warn("[ResourceTarget] 无此枚举：type={}", type);
        throw new IllegalArgumentException("请传入枚举类型：" + Arrays.toString(ResourceTarget.values()));
    }

    @Override
    public String getCode() {
        return type;
    }
}

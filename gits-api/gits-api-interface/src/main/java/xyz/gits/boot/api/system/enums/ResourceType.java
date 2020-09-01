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
 * 资源类型[A:顶级目录;B:资源;C:按钮;D:链接]
 *
 * @author songyinyin
 */
@Slf4j
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResourceType implements CodeEnum {

    /**
     * 顶级目录
     */
    A("A", "顶级目录"),

    /**
     * 资源
     */
    B("B", "资源"),

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
     * 反序列化时的初始化函数
     */
    @JsonCreator
    public static ResourceType getItem(@JsonProperty("code") String code) {
        for (ResourceType item : values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        log.warn("[ResourceType] 枚举反序列化异常：code={}", code);
        throw new IllegalArgumentException("请传入枚举类型：" + Arrays.toString(ResourceType.values()));
    }

    /**
     * code转枚举类型
     *
     * @param code code码
     * @return {@link ResourceType}
     * @author dingmingyang
     * @date 2020/6/5 11:36
     */
    public static ResourceType fromString(String code) {
        for (ResourceType b : ResourceType.values()) {
            if (b.code.equalsIgnoreCase(code)) {
                return b;
            }
        }
        log.warn("[ResourceType] 无此枚举：code={}", code);
        throw new IllegalArgumentException("请传入枚举类型：" + Arrays.toString(ResourceType.values()));
    }
}

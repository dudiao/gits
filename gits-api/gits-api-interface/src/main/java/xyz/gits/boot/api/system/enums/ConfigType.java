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
 * 配置中心类型[SYSTEM:系统设置;BUSINESS:业务设置]
 *
 * @author luojie
 * @date 2020/5/27 10:53
 * @since 1.0
 */
@Slf4j
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ConfigType implements CodeEnum {


    /**
     * 系统设置
     */
    SYSTEM("SYSTEM", "系统设置"),

    /**
     * 业务设置
     */
    BUSINESS("BUSINESS", "业务设置");

    @EnumValue
    private String code;

    private String message;

    ConfigType() {
    }


    ConfigType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 反序列化时的初始化函数
     */
    @JsonCreator
    public static ConfigType geItem(@JsonProperty("code") String code) {
        for (ConfigType item : values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        log.warn("[ConfigType] 枚举反序列化异常：code={}", code);
        throw new IllegalArgumentException("请传入枚举类型：" + Arrays.toString(ConfigType.values()));
    }

    /**
     * code转枚举类型
     *
     * @param code code码
     * @return {@link ConfigType}
     * @author dingmingyang
     * @date 2020/6/5 11:36
     */
    public static ConfigType fromString(String code) {
        for (ConfigType b : ConfigType.values()) {
            if (b.code.equalsIgnoreCase(code)) {
                return b;
            }
        }
        log.warn("[ConfigType] 无此枚举：code={}", code);
        throw new IllegalArgumentException("请传入枚举类型：" + Arrays.toString(ConfigType.values()));
    }
}

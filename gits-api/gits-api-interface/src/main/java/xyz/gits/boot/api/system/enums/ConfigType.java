package xyz.gits.boot.api.system.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import xyz.gits.boot.common.core.enums.CodeEnum;

/**
 * 配置中心类型[SYSTEM:系统设置;BUSINESS:业务设置]
 *
 * @author null
 * @date 2020/5/27 10:53
 * @since 1.0
 */
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
     * code转枚举类型
     *
     * @param code code码
     * @return {@link ConfigType}
     * @author null
     * @date 2020/6/5 11:36
     */
    public static ConfigType fromString(String code) {
        for (ConfigType b : ConfigType.values()) {
            if (b.code.equalsIgnoreCase(code)) {
                return b;
            }
        }
        return null;
    }
}

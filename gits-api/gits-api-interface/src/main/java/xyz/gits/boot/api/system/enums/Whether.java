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
 * 是否[0:否;1是]
 *
 * @author dingmingyang
 * @date 2020/06/04/11:42
 */
@Slf4j
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Whether implements CodeEnum {

    /**
     * 否
     */
    NO("0", "否"),
    /**
     * 是
     */
    YES("1", "是");
    @EnumValue
    private String code;

    private String message;

    Whether() {
    }

    Whether(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 反序列化时的初始化函数
     */
    @JsonCreator
    public static Whether getItem(@JsonProperty("code") String code) {
        for (Whether item : values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        log.warn("[Whether] 枚举反序列化异常：code={}", code);
        throw new IllegalArgumentException("请传入枚举类型：" + Arrays.toString(Whether.values()));
    }

    /**
     * code转枚举类型
     *
     * @param code code码
     * @return {@link Whether}
     * @author dingmingyang
     * @date 2020/6/5 11:36
     */
    public static Whether fromString(String code) {
        for (Whether b : Whether.values()) {
            if (b.code.equalsIgnoreCase(code)) {
                return b;
            }
        }
        log.warn("[Whether] 无此枚举：code={}", code);
        throw new IllegalArgumentException("请传入枚举类型：" + Arrays.toString(Whether.values()));
    }
}

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
 * 启停状态[0:启用;1:停用]
 *
 * @author dingmingyang
 * @date 2020/06/04/11:26
 */
@Slf4j
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StopStatus implements CodeEnum {

    /**
     * 启用
     */
    ENABLE("0", "启用"),

    /**
     * 停用
     */
    STOP("1", "停用");
    @EnumValue
    private String code;
    private String message;

    StopStatus() {
    }

    StopStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 反序列化时的初始化函数
     */
    @JsonCreator
    public static StopStatus getItem(@JsonProperty("code") String code) {
        for (StopStatus item : values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        log.warn("[StopStatus] 枚举反序列化异常：code={}", code);
        throw new IllegalArgumentException("请传入枚举类型：" + Arrays.toString(StopStatus.values()));
    }

    /**
     * code转枚举类型
     *
     * @param code code码
     * @return {@link StopStatus}
     * @author dingmingyang
     * @date 2020/6/5 11:36
     */
    public static StopStatus fromString(String code) {
        for (StopStatus b : StopStatus.values()) {
            if (b.code.equalsIgnoreCase(code)) {
                return b;
            }
        }
        log.warn("[StopStatus] 无此枚举：code={}", code);
        throw new IllegalArgumentException("请传入枚举类型：" + Arrays.toString(StopStatus.values()));
    }
}

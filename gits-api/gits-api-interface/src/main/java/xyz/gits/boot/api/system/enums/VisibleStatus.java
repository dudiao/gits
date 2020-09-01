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
 * 显示状态[0:显示;1:隐藏]
 *
 * @author dingmingyang
 * @date 2020/06/04/11:09
 */
@Slf4j
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum VisibleStatus implements CodeEnum {

    /**
     * 显示
     */
    SHOW("0", "显示"),

    /**
     * 隐藏
     */
    LATENT("1", "隐藏");
    @EnumValue
    private String code;
    private String message;

    VisibleStatus() {
    }

    VisibleStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 反序列化时的初始化函数
     */
    @JsonCreator
    public static VisibleStatus getItem(@JsonProperty("code") String code) {
        for (VisibleStatus item : values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        log.warn("[VisibleStatus] 枚举反序列化异常：code={}", code);
        throw new IllegalArgumentException("请传入枚举类型：" + Arrays.toString(VisibleStatus.values()));
    }

    /**
     * code转枚举类型
     *
     * @param code code码
     * @return {@link VisibleStatus}
     * @author dingmingyang
     * @date 2020/6/5 11:36
     */
    public static VisibleStatus fromString(String code) {
        for (VisibleStatus b : VisibleStatus.values()) {
            if (b.code.equalsIgnoreCase(code)) {
                return b;
            }
        }
        log.warn("[VisibleStatus] 无此枚举：code={}", code);
        throw new IllegalArgumentException("请传入枚举类型：" + Arrays.toString(VisibleStatus.values()));
    }
}

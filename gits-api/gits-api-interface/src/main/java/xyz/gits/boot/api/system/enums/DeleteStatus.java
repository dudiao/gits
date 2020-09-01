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
 * 删除状态[0:未删除;1:已删除]
 *
 * @author dingmingyang
 * @date 2020/06/04/11:08
 */
@Slf4j
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DeleteStatus implements CodeEnum {

    /**
     * 未删除
     */
    UN_DELETED("0", "未删除"),
    /**
     * 已删除
     */
    DELETED("1", "已删除");

    @EnumValue
    private String code;

    private String message;

    DeleteStatus() {
    }

    DeleteStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 反序列化时的初始化函数
     */
    @JsonCreator
    public static DeleteStatus getItem(@JsonProperty("code") String code) {
        for (DeleteStatus item : values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        log.warn("[DeleteStatus] 枚举反序列化异常：code={}", code);
        throw new IllegalArgumentException("请传入枚举类型：" + Arrays.toString(DeleteStatus.values()));
    }

    /**
     * code转枚举类型
     *
     * @param code code码
     * @return {@link DeleteStatus}
     * @author dingmingyang
     * @date 2020/6/5 11:36
     */
    public static DeleteStatus fromString(String code) {
        for (DeleteStatus b : DeleteStatus.values()) {
            if (b.code.equalsIgnoreCase(code)) {
                return b;
            }
        }
        log.warn("[DeleteStatus] 无此枚举：code={}", code);
        throw new IllegalArgumentException("请传入枚举类型：" + Arrays.toString(DeleteStatus.values()));
    }
}


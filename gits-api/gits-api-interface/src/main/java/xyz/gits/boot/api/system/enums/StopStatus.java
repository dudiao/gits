package xyz.gits.boot.api.system.enums;

import xyz.gits.boot.common.core.enums.CodeEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 * 启停状态[0:启用;1:停用]
 *
 * @author null
 * @date 2020/06/04/11:26
 */
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
     * code转枚举类型
     *
     * @param code code码
     * @return {@link StopStatus}
     * @author null
     * @date 2020/6/5 11:36
     */
    public static StopStatus fromString(String code) {
        for (StopStatus b : StopStatus.values()) {
            if (b.code.equalsIgnoreCase(code)) {
                return b;
            }
        }
        return null;
    }
}

package xyz.gits.boot.api.system.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import xyz.gits.boot.common.core.enums.CodeEnum;

/**
 * 启用标识
 *
 * @author dingmingyang
 * @date 2020/04/03/16:27
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StopFlag implements CodeEnum {
    ENABLE("0", "启用"),

    STOP("1", "停用");
    @EnumValue
    private String code;

    private String message;

    StopFlag(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

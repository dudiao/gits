package xyz.gits.boot.api.system.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import xyz.gits.boot.common.core.enums.CodeEnum;

/**
 * 锁定标识
 *
 * @author dingmingyang
 * @date 2020/04/03/17:18
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum LockFlag implements CodeEnum {
    LOCKED("1", "已锁定"),

    UN_LOCKED("0", "未锁定");
    @EnumValue
    private String code;

    private String message;

    LockFlag(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

package xyz.gits.boot.api.system.enums;

import xyz.gits.boot.common.core.enums.CodeEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 * 删除状态[0:未删除;1:已删除]
 *
 * @author null
 * @date 2020/06/04/11:08
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DeleteStatus implements CodeEnum {

    UN_DELETED("0", "未删除"),
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
     * code转枚举类型
     *
     * @param code code码
     * @return {@link DeleteStatus}
     * @author null
     * @date 2020/6/5 11:36
     */
    public static DeleteStatus fromString(String code) {
        for (DeleteStatus b : DeleteStatus.values()) {
            if (b.code.equalsIgnoreCase(code)) {
                return b;
            }
        }
        return null;
    }
}

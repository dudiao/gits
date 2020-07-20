package xyz.gits.boot.api.system.enums;

import xyz.gits.boot.common.core.enums.CodeEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 * 显示状态[0:显示;1:隐藏]
 *
 * @author null
 * @date 2020/06/04/11:09
 */
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
     * code转枚举类型
     *
     * @param code code码
     * @return {@link VisibleStatus}
     * @author null
     * @date 2020/6/5 11:36
     */
    public static VisibleStatus fromString(String code) {
        for (VisibleStatus b : VisibleStatus.values()) {
            if (b.code.equalsIgnoreCase(code)) {
                return b;
            }
        }
        return null;
    }
}

package xyz.gits.boot.api.system.enums;

import lombok.Getter;
import xyz.gits.boot.common.core.enums.CodeEnum;

/**
 * 状态：是否
 *
 * @author dingmingyang
 * @date 2020/01/30/14:25
 */
@Getter
public enum Status implements CodeEnum {

    YES("1", "是"),
    NO("0", "否");

    private String code;

    private String message;

    Status(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

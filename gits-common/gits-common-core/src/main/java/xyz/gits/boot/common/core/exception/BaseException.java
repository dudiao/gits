package xyz.gits.boot.common.core.exception;

import lombok.Getter;

/**
 * 自定义异常基类
 *
 * @author songyinyin
 * @date 2020/1/17 17:07
 */
@Getter
public class BaseException extends NestedRuntimeException {

    protected int code;

    public BaseException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BaseException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}

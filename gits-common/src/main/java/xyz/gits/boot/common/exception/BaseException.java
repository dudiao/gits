package xyz.gits.boot.common.exception;

import lombok.Getter;
import xyz.gits.boot.common.response.ResponseCode;

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

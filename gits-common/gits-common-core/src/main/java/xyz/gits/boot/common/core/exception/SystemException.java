package xyz.gits.boot.common.core.exception;

import lombok.Getter;
import xyz.gits.boot.common.core.response.ResponseCode;

/**
 * 系统管理模块 异常
 *
 * @author songyinyin
 * @date 2020/3/14 下午 08:46
 */
@Getter
public class SystemException extends BaseException {

    public SystemException(ResponseCode responseCode) {
        super(responseCode.getCode(), responseCode.getMessage());
    }

    /**
     * 抛出异常，该异常会被全局异常拦截打印出来
     *
     * @param responseCode 状态码
     * @param cause        异常信息
     */
    public SystemException(ResponseCode responseCode, Throwable cause) {
        super(responseCode.getCode(), responseCode.getMessage(), cause);
    }
}

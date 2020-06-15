package xyz.gits.boot.common.core.exception;

import lombok.Getter;
import xyz.gits.boot.common.core.response.ResponseCode;

/**
 * 系统管理模块异常，不会打印日志信息，仅返回前端结果
 *
 * @author songyinyin
 * @date 2020/3/14 下午 08:46
 */
@Getter
public class SystemNoLogException extends BaseException {

    public SystemNoLogException(ResponseCode responseCode) {
        super(responseCode.getCode(), responseCode.getMessage());
    }

}

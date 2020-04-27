package xyz.gits.boot.common.core.response;

import lombok.Data;

import java.io.Serializable;

/**
 * 基础返回结果 实体
 *
 * @author songyinyin
 * @date 2020/3/14 下午 09:15
 */
@Data
public class BaseResponse implements Serializable {

    protected int code = ResponseCode.SUCCESS.getCode();
    protected String message = ResponseCode.SUCCESS.getMessage();

    public BaseResponse() {
    }

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

package xyz.gits.boot.security.login.verifycode;

import org.springframework.security.core.AuthenticationException;
import xyz.gits.boot.common.core.response.ResponseCode;

/**
 * 验证码异常
 *
 * @author songyinyin
 * @date 2020/5/31 下午 12:20
 */
public class VerifyCodeException extends AuthenticationException {
    public VerifyCodeException() {
        super(ResponseCode.VERIFY_CODE_ERROR.getMessage());
    }
}

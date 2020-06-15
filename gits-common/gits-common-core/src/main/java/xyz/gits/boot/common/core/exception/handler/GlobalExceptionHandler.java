package xyz.gits.boot.common.core.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.gits.boot.common.core.exception.SystemException;
import xyz.gits.boot.common.core.exception.SystemNoLogException;
import xyz.gits.boot.common.core.response.ResponseCode;
import xyz.gits.boot.common.core.response.RestResponse;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 全局异常拦截
 *
 * @author songyinyin
 * @date 2020/1/17 17:11
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 系统异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(SystemException.class)
    public RestResponse<ResponseCode> systemExceptionHandler(SystemException exception) {
        log.error(String.format("处理异常 code=%s，message=%s", exception.getCode(), exception.getMessage()), exception);
        return RestResponse.build(exception.getCode(), exception.getMessage());
    }

    /**
     * 系统异常，不打印日志，直接返回前端
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(SystemNoLogException.class)
    public RestResponse<ResponseCode> systemNoLogExceptionHandler(SystemNoLogException exception) {
        return RestResponse.build(exception.getCode(), exception.getMessage());
    }

    /**
     * 非法参数异常
     *
     * @param response
     * @param ex
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public RestResponse illegalArgumentExceptionHandler(HttpServletResponse response, IllegalArgumentException ex) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        log.error(ex.getMessage(), ex);
        return RestResponse.build(ResponseCode.GLOBAL_PARAM_ERROR.getCode(), ex.getMessage(), null);
    }

    /**
     * 使用注解 {@link javax.validation.Valid}和 {@link org.springframework.validation.annotation.Validated}
     * <b>进行嵌套验证</b>被嵌套的属性验证不通过时会抛出 {@link MethodArgumentNotValidException}
     *
     * @param response
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestResponse methodArgumentNotValidExceptionHandler(HttpServletResponse response, MethodArgumentNotValidException ex) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        log.error(ex.getMessage(), ex);

        BindingResult result = ex.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();
        StringBuilder builder = new StringBuilder();
        for (FieldError error : fieldErrors) {
            builder.append(error.getDefaultMessage() + "\n");
        }
        return RestResponse.build(ResponseCode.GLOBAL_PARAM_ERROR.getCode(), builder.toString(), null);
    }

    /**
     * 使用注解 {@link javax.validation.Valid}和 {@link org.springframework.validation.annotation.Validated}
     * <p>验证不通过时抛出</p>
     *
     * @param response
     * @param ex
     * @return
     */
    @ExceptionHandler(BindException.class)
    public RestResponse bindExceptionHandler(HttpServletResponse response, BindException ex) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        List<ObjectError> errors = ex.getAllErrors();
        log.error(ex.getMessage(), ex);
        StringBuffer errorMsg = new StringBuffer();
        errors.stream().forEach(x ->
            errorMsg.append(x.getDefaultMessage()).append("\n")
        );
        return RestResponse.build(ResponseCode.GLOBAL_PARAM_ERROR.getCode(), errorMsg.toString(), null);
    }
}

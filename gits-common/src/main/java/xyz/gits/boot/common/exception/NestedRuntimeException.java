package xyz.gits.boot.common.exception;

import com.sun.istack.internal.Nullable;

/**
 * 异常基类：包装异常产生的原因
 *
 * @author songyinyin
 * @date 2019/12/9 15:05
 */
public abstract class NestedRuntimeException extends RuntimeException {

    /**
     * Construct a {@code NestedRuntimeException} with the specified detail message.
     *
     * @param msg 异常详细信息
     */
    public NestedRuntimeException(String msg) {
        super(msg);
    }

    /**
     * Construct a {@code NestedRuntimeException} with the specified detail message
     * and nested exception.
     *
     * @param msg   异常详细信息
     * @param cause 需要被嵌套的异常
     */
    public NestedRuntimeException(@Nullable String msg, @Nullable Throwable cause) {
        super(msg, cause);
    }

    /**
     * Return the detail message, including the message from the nested exception
     * if there is one.
     */
    @Override
    @Nullable
    public String getMessage() {
        Throwable cause = getCause();
        String message = super.getMessage();
        if (cause == null) {
            return message;
        }
        StringBuilder sb = new StringBuilder(64);
        if (message != null) {
            sb.append(message).append("; ");
        }
        sb.append("gits-boot nested exception is ").append(cause);
        return sb.toString();
    }

    @Override
    public String toString() {
        String s = getClass().getName();
        String message = getLocalizedMessage();
        return (message != null) ? (s + ": " + message) : s;
    }
}

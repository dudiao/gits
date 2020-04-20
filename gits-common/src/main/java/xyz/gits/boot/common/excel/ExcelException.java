package xyz.gits.boot.common.excel;


import xyz.gits.boot.common.exception.NestedRuntimeException;

/**
 * @author songyinyin
 * @date 2019/12/9 16:05
 */
public class ExcelException extends NestedRuntimeException {
    /**
     * Construct a {@code NestedRuntimeException} with the specified detail message.
     *
     * @param msg 异常详细信息
     */
    public ExcelException(String msg) {
        super(msg);
    }

    /**
     * Construct a {@code NestedRuntimeException} with the specified detail message
     * and nested exception.
     *
     * @param msg   异常详细信息
     * @param cause 需要被嵌套的异常
     */
    public ExcelException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

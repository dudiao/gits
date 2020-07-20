package xyz.gits.boot.system.dto.log;


import xyz.gits.boot.common.core.basic.BaseQueryDTO;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 操作日志传输对象
 *
 * @author zhangyapu
 * @date 2020/5/27 11:39
 */
@Data
public class OperationDTO extends BaseQueryDTO {
    private static final long serialVersionUID = 1L;
    /**
     * 当前操作用户
     */
    private String createUser;

    /**
     * 操作
     */
    private String operation;

    /**
     * 创建时间(页面查询操作时间 createTime)
     */

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createOpenDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createOverDate;

}

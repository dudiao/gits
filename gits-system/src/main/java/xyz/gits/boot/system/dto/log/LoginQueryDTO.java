package xyz.gits.boot.system.dto.log;

import xyz.gits.boot.common.core.basic.BaseQueryDTO;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 登录日志传输对象
 *
 * @author null
 * @date 2020/5/27 11:39
 */
@Data
public class LoginQueryDTO extends BaseQueryDTO {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 登录IP
     */
    private String loginIp;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 登录时间(页面查询操作时间 loginTime)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime loginOpenDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime loginOverDate;
}

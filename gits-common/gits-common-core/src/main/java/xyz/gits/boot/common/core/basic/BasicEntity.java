package xyz.gits.boot.common.core.basic;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 实体类
 *
 * @author songyinyin
 * @date 2020/1/19 10:01
 */
@Data
public  class BasicEntity{
    /**
     * 创建用户
     */
    @TableField("create_user_id")
    private String createUserId;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 编辑用户
     */
    @TableField("update_user_id")
    private String updateUserId;

    /**
     * 编辑时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;
}

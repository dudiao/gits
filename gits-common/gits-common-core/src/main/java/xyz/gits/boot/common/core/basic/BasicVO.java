package xyz.gits.boot.common.core.basic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 实体类
 *
 * @author songyinyin
 * @date 2020/1/19 10:01
 */
@Data
public class BasicVO {

    @ApiModelProperty(value = "创建用户名称")
    private String createUserNickName;

    @ApiModelProperty(value = "创建用户ID")
    private String createUserId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改用户名称")
    private String updateUserNickName;

    @ApiModelProperty(value = "修改用户ID")
    private String updateUserId;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;
}

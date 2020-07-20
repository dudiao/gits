package xyz.gits.boot.system.dto.group;

import xyz.gits.boot.common.core.basic.BaseQueryDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 数据权限组查询Dto
 *
 * @author null
 * @date 2020/5/11 14:06
 * @since 1.0
 */
@Data
@ApiModel("数据权限组查询Dto")
public class GroupQueryDTO extends BaseQueryDTO {

    @ApiModelProperty(value = "组名称")
    @Size(max = 31, message = "组名称不能超过31")
    private String groupName;

    @ApiModelProperty(value = "启停状态[0:启用;1:停用]")
    @Size(max = 1, message = "启停状态不能超过1")
    private String StopStatusCode;

    @ApiModelProperty(value = "开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startCreateDate;

    @ApiModelProperty(value = "结束日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endCreateDate;

    @ApiModelProperty(value = "用户Id集")
    private String userIds;

}

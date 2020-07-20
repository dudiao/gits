package xyz.gits.boot.system.dto.group;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.SpringSecurityCoreVersion;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * 数据权限组编辑DTO
 *
 * @author null
 * @date 2020/5/11 14:06
 * @since 1.0
 */
@Data
@ApiModel("数据权限组编辑DTO")
public class GroupUpdateDTO  implements Serializable {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    @ApiModelProperty(value = "组key", required = true)
    @NotBlank(message = "组key不能为空")
    @Size(max = 31, message = "组key不能超过31")
    private String groupKey;

    @ApiModelProperty(value = "组名称", required = true)
    @NotBlank(message = "组名称不能为空")
    @Size(max = 63, message = "组名称不能超过63")
    private String groupName;

    @ApiModelProperty(value = "启停状态[0:启用;1:停用]", required = true)
    @NotBlank(message = "启停状态不能为空")
    @Size(max = 1, message = "启停状态不能超过1")
    private String StopStatusCode;

    @ApiModelProperty(value = "用户ID集合", required = true)
    @NotEmpty(message = "用户ID集合不能为空")
    private List<String> userIds;

    @ApiModelProperty(value = "机构ID集合", required = true)
    @NotEmpty(message = "机构ID集合不能为空")
    private List<String> orgIds;

    @ApiModelProperty(value = "描述")
    @Size(max = 511, message = "描述不能超过511")
    private String remark;

}

package xyz.gits.boot.system.dto.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import xyz.gits.boot.common.core.basic.BaseQueryDTO;

import javax.validation.constraints.Size;

/**
 * 角色查询传输对象
 *
 * @author null
 * @date 2020/7/10 10:50
 */
@Data
@ApiModel("用户查询参数")
public class RoleQueryDTO extends BaseQueryDTO {

    @ApiModelProperty(value = "角色名称")
    @Size(max = 63, message = "角色名称不能超过63")
    private String roleName;

    @ApiModelProperty(value = "角色代码(英文名称)")
    @Size(max = 255, message = "角色代码不能超过255")
    private String roleCode;
}

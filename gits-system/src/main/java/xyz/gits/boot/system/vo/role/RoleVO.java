package xyz.gits.boot.system.vo.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色对象VO
 *
 * @author null
 * @date 2020/07/20/14:54
 */

@Data
@ApiModel(value = "角色对象VO")
public class RoleVO {

    @ApiModelProperty(value = "角色ID")
    private String roleId;

    @ApiModelProperty(value = "角色代码(英文名称)")
    private String roleCode;

    @ApiModelProperty(value = "角色名称")
    private String roleName;


}
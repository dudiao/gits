package xyz.gits.boot.system.vo.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import xyz.gits.boot.api.system.enums.Status;
import xyz.gits.boot.common.core.basic.BasicVO;

/**
 * 角色详情VO
 *
 * @author null
 * @date 2020/5/715:11
 */
@Data
@ApiModel(value = "角色详情VO")
public class RoleDetailVO extends BasicVO {

    @ApiModelProperty(value = "角色ID")
    private String roleId;

    @ApiModelProperty(value = "角色代码(英文名称)")
    private String roleCode;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色描述")
    private String roleDesc;

    @ApiModelProperty(value = "创建机构代码")
    private String createOrgId;

    @ApiModelProperty(value = "创建机构名称")
    private String createOrgName;

    @ApiModelProperty(value = "有效状态[0:有效;1:无效]")
    private Status status;

    @ApiModelProperty(value = "停用原因")
    private String stopReason;
}

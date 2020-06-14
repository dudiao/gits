package xyz.gits.boot.api.system.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import xyz.gits.boot.api.system.enums.StopFlag;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author songyinyin
 * @date 2020/6/14 下午 03:22
 */
@Data
public class RoleUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色ID")
    @NotBlank(message = "角色ID不能为空")
    @Size(max = 32, message = "角色ID不能超过32")
    private String roleId;

    @ApiModelProperty(value = "角色代码")
    @NotBlank(message = "角色代码不能为空")
    @Size(max = 32, message = "角色代码不能超过32")
    private String roleCode;

    @ApiModelProperty(value = "角色名称")
    @NotBlank(message = "角色名称不能为空")
    @Size(max = 64, message = "角色名称不能超过64")
    private String roleName;

    @ApiModelProperty(value = "角色描述")
    private String roleDesc;

    @ApiModelProperty(value = "启停标志（0启用1停用）")
    private StopFlag stopFlag;

    @ApiModelProperty(value = "停用原因")
    private String stopReason;

}

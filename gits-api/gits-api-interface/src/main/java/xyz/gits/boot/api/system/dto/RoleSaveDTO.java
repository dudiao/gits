package xyz.gits.boot.api.system.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author songyinyin
 * @date 2020/6/14 下午 03:22
 */
@Data
public class RoleSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

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

}

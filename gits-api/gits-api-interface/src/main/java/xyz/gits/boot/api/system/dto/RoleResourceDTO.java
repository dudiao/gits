package xyz.gits.boot.api.system.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author songyinyin
 * @date 2020/6/14 下午 06:28
 */
@Data
public class RoleResourceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色ID")
    @NotBlank(message = "角色ID不能为空")
    @Size(max = 32, message = "角色ID不能超过32")
    private String roleId;

    @ApiModelProperty(value = "菜单id列表，多个用英文逗号分隔")
    private String menuIds;
}

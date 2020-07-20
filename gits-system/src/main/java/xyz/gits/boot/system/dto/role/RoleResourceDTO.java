package xyz.gits.boot.system.dto.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 角色资源添加DTO
 *
 * @author null
 * @date 2020/07/14/16:41
 */
@Data
@ApiModel(value = "角色资源添加DTO")
public class RoleResourceDTO {

    @ApiModelProperty(value = "角色ID", required = true)
    @NotBlank(message = "角色ID不能为空")
    @Size(max = 32, message = "角色ID不能超过32")
    private String roleId;

    @ApiModelProperty(value = "资源id集合")
    List<String> resourceIds;
}
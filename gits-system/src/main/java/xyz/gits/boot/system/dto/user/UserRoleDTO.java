package xyz.gits.boot.system.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 用户角色添加DTO
 *
 * @author null
 * @date 2020/07/14/16:39
 */
@Data
@ApiModel(value = "用户角色添加DTO")
public class UserRoleDTO {

    @ApiModelProperty(value = "用户id", required = true)
    @NotBlank(message = "用户id不能为空")
    @Size(max = 31, message = "用户id不能超过31")
    String userId;

    @ApiModelProperty(value = "角色id集合")
    List<String> roleIds;
}
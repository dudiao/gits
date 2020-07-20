package xyz.gits.boot.system.dto.role;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.SpringSecurityCoreVersion;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统修改角色传输对象
 *
 * @author null
 * @date 2020/5/7 14:41
 */
@Data
@ApiModel(value = "系统修改角色对象")
public class RoleUpdateDTO  implements Serializable {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    @ApiModelProperty(value = "角色ID", required = true)
    @NotBlank(message = "角色ID不能为空")
    @Size(max = 32, message = "角色ID不能超过32")
    private String roleId;

    @ApiModelProperty(value = "角色名称", required = true)
    @NotBlank(message = "角色名称不能为空")
    @Size(max = 63, message = "角色ID不能超过63")
    private String roleName;

    @ApiModelProperty(value = "角色代码(英文名称)", required = true)
    @NotBlank(message = "角色代码不能为空")
    @Size(max = 63, message = "角色代码不能超过63")
    private String roleCode;

    @ApiModelProperty(value = "角色描述")
    @Size(max = 31, message = "角色描述不能超过31")
    private String roleDesc;
}

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
 * 系统新增角色传输对象
 *
 * @author null
 * @date 2020/5/22 16:43
 */
@Data
@ApiModel(value = "系统新增角色对象")
public class RoleSaveDTO  implements Serializable {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    @ApiModelProperty(value = "角色名称", required = true)
    @NotBlank(message = "角色名称不能为空")
    @Size(max = 63, message = "角色ID不能超过63")
    private String roleName;

    @ApiModelProperty(value = "角色代码(英文名称)", required = true)
    @NotBlank(message = "角色代码不能为空")
    @Size(max = 255, message = "角色代码不能超过255")
    private String roleCode;

    @ApiModelProperty(value = "角色描述")
    @Size(max = 63, message = "角色描述不能超过63")
    private String roleDesc;

}

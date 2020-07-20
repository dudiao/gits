package xyz.gits.boot.system.dto.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.SpringSecurityCoreVersion;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 配置传输对象
 *
 * @author: null
 * @date: 2020/6/12 9:38
 */
@Data
@ApiModel("配置传输对象")
public class ConfigDTO implements Serializable {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    @ApiModelProperty(value = "配置Id",required = true)
    @NotBlank(message = "配置KEY不能为空")
    @Size(max = 63, message = "配置KEY不能超过63")
    private String configId;

    @ApiModelProperty(value = "配置名称", required = true)
    @NotBlank(message = "配置名称不能为空")
    @Size(max = 63, message = "配置名称不能超过63")
    private String configName;

    @ApiModelProperty(value = "配置类型[SYSTEM:系统设置;BUSINESS:业务设置]", required = true)
    @NotBlank(message = "配置项类型不能为空")
    @Size(max = 31, message = "31")
    private String configType;

    @ApiModelProperty(value = "配置项默认Values值", required = true)
    @NotBlank(message = "配置项默认Values值不能为空")
    @Size(max = 255, message = "配置项默认Values值不能超过255")
    private String configDefaultValue;

    @ApiModelProperty(value = "配置项Values值", required = true)
    @NotBlank(message = "配置项Values值不能为空")
    @Size(max = 255, message = "配置项Values值不能超过255")
    private String configValues;

    @ApiModelProperty(value = "排序(DESC)")
    @Max(value = 9999, message = "排序不能大于9999")
    private Integer orderNum;

    @ApiModelProperty(value = "备注说明")
    @Size(max = 511, message = "备注说明不能超过511")
    private String remark;

    @ApiModelProperty(value = "配置组")
    @Size(max = 11, message = "配置组不能超过11")
    private String configGroup;

}

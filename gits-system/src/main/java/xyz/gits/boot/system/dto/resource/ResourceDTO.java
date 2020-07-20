package xyz.gits.boot.system.dto.resource;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.SpringSecurityCoreVersion;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 系统资源传输对象
 *
 * @author null
 * @date 2020/4/2815:02
 */
@Data
@ApiModel(value = "系统资源传输对象")
public class ResourceDTO  implements Serializable {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    @ApiModelProperty(value = "资源ID", required = true)
    @NotBlank(message = "资源ID不能为空")
    @Size(max = 31, message = "资源ID不能超过31")
    private String resourceId;

    @ApiModelProperty(value = "父资源ID", required = true)
    @NotBlank(message = "资源父ID不能为空")
    @Size(max = 31, message = "资源父ID不能超过31")
    private String parentResourceId;

    @ApiModelProperty(value = "资源名称", required = true)
    @NotBlank(message = "资源名称不能为空")
    @Size(max = 31, message = "资源名称不能超过31")
    private String resourceName;

    @ApiModelProperty(value = "资源地址")
    @Size(max = 255, message = "资源URL不能超过255")
    private String url;

    @ApiModelProperty(value = "资源类型[A:顶级目录;B:资源;C:按钮;D:链接]", required = true)
    @NotBlank(message = "资源类型不能为空")
    @Size(max = 1, message = "资源类型不能超过1")
    private String resourceTypeCode;

    @ApiModelProperty(value = "显示状态[0:显示;1:隐藏]", required = true)
    @NotBlank(message = "资源状态不能为空")
    @Size(max = 1, message = "资源状态不能超过1")
    private String visibleCode;

    @ApiModelProperty(value = "资源排序，越大越排在上边")
    @Max(value = 9999, message = "排序不能大于9999")
    private Integer orderNum;

    @ApiModelProperty(value = "权限字符串")
    @Size(max = 127, message = "权限字符串不能超过127")
    private String permission;

    @ApiModelProperty(value = "资源打开方式[menuItem:页签;menuBlank:弹窗;link:链接]")
    @Size(max = 31, message = "打开方式不能超过31")
    private String targetCode;

    @ApiModelProperty(value = "组件路径")
    @Size(max = 255, message = "组件路径不能超过255")
    private String component;

    @ApiModelProperty(value = "资源图标")
    @Size(max = 255, message = "资源图标不能超过255")
    private String icon;

    @ApiModelProperty(value = "备注")
    @Size(max = 255, message = "备注不能超过255")
    private String remark;
}

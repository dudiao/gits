package xyz.gits.boot.api.system.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author songyinyin
 * @date 2020/6/15 下午 09:24
 */
@Data
public class ResourceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "资源ID")
    @NotBlank(message = "资源ID不能为空")
    @Size(max = 32, message = "资源ID不能超过32")
    private String resourceId;

    @ApiModelProperty(value = "父资源ID")
    @NotBlank(message = "父资源ID不能为空")
    @Size(max = 32, message = "父资源ID不能超过32")
    private String parentResourceId;

    @ApiModelProperty(value = "资源名称")
    @NotBlank(message = "资源名称不能为空")
    @Size(max = 100, message = "资源名称长度不能超过100")
    private String resourceName;

    @ApiModelProperty(value = "资源地址")
    @Size(max = 255, message = "资源地址长度不能超过255")
    private String url;

    @ApiModelProperty(value = "资源类型(A-系统、B-菜单、C-按钮、D-链接)")
    private String resourceTypeCode;

    @ApiModelProperty(value = "资源排序，越大越排在上边")
    private Integer orderNum;

    @ApiModelProperty(value = "权限字符串")
    @Size(max = 50, message = "权限字符串长度不能超过50")
    private String permission;

    @ApiModelProperty(value = "资源图标")
    @Size(max = 100, message = "资源图标长度不能超过100")
    private String icon;

    @ApiModelProperty(value = "显示状态[0:显示;1:隐藏]")
    @NotBlank(message = "菜单状态不能为空")
    @Size(max = 1, message = "菜单状态不能超过1")
    private String visibleCode;

    @ApiModelProperty(value = "备注")
    @Size(max = 400, message = "备注字数不能超过400")
    private String remark;
}

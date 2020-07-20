package xyz.gits.boot.system.vo.resource;

import xyz.gits.boot.api.system.enums.ResourceTarget;
import xyz.gits.boot.api.system.enums.ResourceType;
import xyz.gits.boot.api.system.enums.VisibleStatus;
import xyz.gits.boot.common.core.basic.BasicVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 资源详情VO
 *
 * @author null
 * @date 2020/5/14 10:35
 * @since 1.0
 */
@Data
@ApiModel("资源详情VO")
public class ResourceDetailVO extends BasicVO {

    @ApiModelProperty(value = "父资源名称")
    private String parentName;

    @ApiModelProperty(value = "资源名称")
    private String resourceName;

    @ApiModelProperty(value = "资源ID")
    private String resourceId;

    @ApiModelProperty(value = "父资源ID")
    private String parentResourceId;

    @ApiModelProperty(value = "资源地址")
    private String url;

    @ApiModelProperty(value = "资源类型[A:顶级目录;B:资源;C:按钮;D:链接]")
    private ResourceType resourceType;

    @ApiModelProperty(value = "显示状态[0:显示;1:隐藏]")
    private VisibleStatus visible;

    @ApiModelProperty(value = "资源排序，越大越排在上边")
    private Integer orderNum;

    @ApiModelProperty(value = "资源打开方式[menuItem:页签;menuBlank:弹窗;link:链接]")
    private ResourceTarget target;

    @ApiModelProperty(value = "组件路径")
    private String component;

    @ApiModelProperty(value = "权限字符串")
    private String permission;

    @ApiModelProperty(value = "资源图标")
    private String icon;
}

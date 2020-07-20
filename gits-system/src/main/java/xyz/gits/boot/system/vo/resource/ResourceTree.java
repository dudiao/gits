package xyz.gits.boot.system.vo.resource;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.gits.boot.api.system.enums.ResourceType;
import xyz.gits.boot.api.system.enums.VisibleStatus;
import xyz.gits.boot.common.core.utils.TreeNode;

import java.time.LocalDateTime;

/**
 * 资源详情树
 *
 * @author songyinyin
 * @date 2020/6/14 下午 09:31
 */
@Data
@ApiModel(value = "资源详情树")
@EqualsAndHashCode(callSuper = true)
public class ResourceTree extends TreeNode<ResourceTree> {

    @ApiModelProperty(value = "资源名称")
    private String resourceName;

    @ApiModelProperty(value = "资源地址")
    private String url;

    @ApiModelProperty(value = "资源类型(A-系统、B-菜单、C-按钮、D-链接)")
    private ResourceType resourceType;

    @ApiModelProperty(value = "资源状态（0显示 1隐藏）")
    private VisibleStatus visible;

    @ApiModelProperty(value = "资源排序，越大越排在上边")
    private Integer orderNum;

    @ApiModelProperty(value = "权限字符串")
    private String permission;

    @ApiModelProperty(value = "资源图标")
    private String icon;

    @ApiModelProperty(value = "创建用户")
    private String createUserId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "编辑用户")
    private String updateUserId;

    @ApiModelProperty(value = "编辑时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;
}

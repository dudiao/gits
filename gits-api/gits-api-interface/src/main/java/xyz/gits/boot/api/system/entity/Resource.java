package xyz.gits.boot.api.system.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 资源表
 * </p>
 *
 * @author songyinyin
 * @date 2020-02-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("system_resource")
@ApiModel(value="Resource对象", description="资源表")
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "资源ID")
    @TableId("resource_id")
    private String resourceId;

    @ApiModelProperty(value = "父资源ID")
    @TableField("parent_resource_id")
    private String parentResourceId;

    @ApiModelProperty(value = "资源名称")
    @TableField("resource_name")
    private String resourceName;

    @ApiModelProperty(value = "资源地址")
    @TableField("url")
    private String url;

    @ApiModelProperty(value = "资源类型(A-系统、B-菜单、C-按钮、D-链接)")
    @TableField("resource_type")
    private String resourceType;

    @ApiModelProperty(value = "资源状态（1显示 0隐藏）")
    @TableField("visible")
    private String visible;

    @ApiModelProperty(value = "资源排序，越大越排在上边")
    @TableField("order_num")
    private BigDecimal orderNum;

    @ApiModelProperty(value = "权限字符串")
    @TableField("permission")
    private String permission;

    @ApiModelProperty(value = "资源图标")
    @TableField("icon")
    private String icon;

    @ApiModelProperty(value = "创建用户")
    @TableField("create_user_id")
    private String createUserId;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "编辑用户")
    @TableField("update_user_id")
    private String updateUserId;

    @ApiModelProperty(value = "编辑时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;


}

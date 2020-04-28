package xyz.gits.boot.api.system.entity;

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
import xyz.gits.boot.api.system.enums.Status;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author songyinyin
 * @date 2020-02-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("system_role")
@ApiModel(value="Role对象", description="角色表")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色ID")
    @TableId("role_id")
    private String roleId;

    @ApiModelProperty(value = "角色名称")
    @TableField("role_name")
    private String roleName;

    @ApiModelProperty(value = "角色描述")
    @TableField("role_desc")
    private String roleDesc;

    @ApiModelProperty(value = "创建机构代码")
    @TableField("create_org_id")
    private String createOrgId;

    @ApiModelProperty(value = "启停标志（1启用0停用）")
    @TableField("status")
    private Status status;

    @ApiModelProperty(value = "停用原因")
    @TableField("stop_reason")
    private String stopReason;

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


}

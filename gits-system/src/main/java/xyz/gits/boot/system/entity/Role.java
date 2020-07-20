package xyz.gits.boot.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import xyz.gits.boot.api.system.enums.Status;
import xyz.gits.boot.common.core.basic.BasicEntity;

import java.io.Serializable;

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
@ApiModel(value = "Role对象", description = "角色表")
public class Role extends BasicEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @TableId("role_id")
    private String roleId;

    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;

    /**
     * 角色代码(英文名称)
     */
    @TableField("role_code")
    private String roleCode;

    /**
     * 角色描述
     */
    @TableField("role_desc")
    private String roleDesc;

    /**
     * 启停状态[0:启用;1:停用]
     */
    @TableField("stop_status")
    private Status status;

    /**
     * 停用原因
     */
    @TableField("stop_reason")
    private String stopReason;

    /**
     * 创建机构代码
     */
    @TableField("create_org_id")
    private String createOrgId;

}

package xyz.gits.boot.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import xyz.gits.boot.common.core.basic.BasicEntity;

import java.io.Serializable;

/**
 * 系统用户拥有角色关联表
 *
 * @author songyinyin
 * @date 2020-02-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("system_user_role_rel")
@ApiModel(value = "UserRoleRel对象", description = "系统用户拥有角色关联表")
public class UserRoleRel extends BasicEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private String id;

    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 角色ID
     */
    @TableField("role_id")
    private String roleId;

}

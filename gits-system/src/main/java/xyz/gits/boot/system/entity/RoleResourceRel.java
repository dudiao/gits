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
import java.time.LocalDateTime;

/**
 * <p>
 * 角色资源关联表
 * </p>
 *
 * @author songyinyin
 * @date 2020-02-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("system_role_resource_rel")
@ApiModel(value = "RoleResourceRel对象", description = "角色资源关联表")
public class RoleResourceRel extends BasicEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private String id;

    /**
     * 角色ID
     */
    @TableField("role_id")
    private String roleId;

    /**
     * 资源ID
     */
    @TableField("resource_id")
    private String resourceId;


}

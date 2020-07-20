package xyz.gits.boot.system.entity;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;
import xyz.gits.boot.api.system.enums.ResourceTarget;
import xyz.gits.boot.api.system.enums.ResourceType;
import xyz.gits.boot.api.system.enums.VisibleStatus;
import xyz.gits.boot.common.core.basic.BasicEntity;

import java.io.Serializable;
import java.util.Objects;

/**
 * <p>
 * 资源表
 * </p>
 *
 * @author songyinyin
 * @date 2020-02-29
 */
@Data
@Accessors(chain = true)
@TableName("system_resource")
@ApiModel(value = "Resource对象", description = "资源表")
public class Resource extends BasicEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 资源ID
     */
    @TableId("resource_id")
    private String resourceId;

    /**
     * 资源名称
     */
    @TableField("resource_name")
    private String resourceName;

    /**
     * 父资源ID
     */
    @TableField("parent_resource_id")
    private String parentResourceId;

    /**
     * 父资源名称
     */
    @TableField(exist = false)
    private String parentName;

    /**
     * 资源地址
     */
    @TableField("url")
    private String url;

    /**
     * 资源类型[A:顶级目录;B:菜单;C:按钮;D:链接]
     */
    @TableField("resource_type")
    private ResourceType resourceType;

    /**
     * 菜单打开方式[menuItem:页签;menuBlank:弹窗;link:链接]
     */
    @TableField("target")
    private ResourceTarget target;

    /**
     * 组件路径
     */
    @TableField("component")
    private String component;

    /**
     * 资源图标
     */
    @TableField("permission")
    private String permission;

    /**
     * 资源图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 显示状态[0:显示;1:隐藏]
     */
    @TableField("visible")
    private VisibleStatus visible;

    /**
     * 资源排序，越大越排在上边
     */
    @TableField("order_num")
    private Integer orderNum;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Resource) {
            String targetResourceId = ((Resource) obj).getResourceId();
            return StrUtil.equals(resourceId, targetResourceId);
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resourceId);
    }
}

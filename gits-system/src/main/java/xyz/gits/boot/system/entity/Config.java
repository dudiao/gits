package xyz.gits.boot.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import xyz.gits.boot.api.system.enums.ConfigType;
import xyz.gits.boot.common.core.basic.BasicEntity;

import java.io.Serializable;

/**
 * 配置信息-实体类
 *
 * @author songyinyin
 * @since 2020-05-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("system_config")
public class Config extends BasicEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 配置KEY
     */
    @TableId(value = "config_id")
    private String configId;

    /**
     * 配置组
     */
    @TableField("config_group")
    private String configGroup;

    /**
     * 配置名称
     */
    @TableField("config_name")
    private String configName;

    /**
     * 配置中心类型[SYSTEM:系统设置;BUSINESS:业务设置]
     */
    @TableField("config_type")
    private ConfigType configType;

    /**
     * 配置项Values值
     */
    @TableField("config_values")
    private String configValues;

    /**
     * 配置项默认Values值
     */
    @TableField("config_default_value")
    private String configDefaultValue;

    /**
     * 排序(DESC)
     */
    @TableField("order_num")
    private Integer orderNum;

    /**
     * 备注说明
     */
    @TableField("remark")
    private String remark;


}

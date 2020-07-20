package xyz.gits.boot.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import xyz.gits.boot.api.system.enums.StopStatus;
import xyz.gits.boot.common.core.basic.BasicEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 机构表
 * </p>
 *
 * @author songyinyin
 * @date 2020-02-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("system_org")
@ApiModel(value = "Org对象", description = "机构表")
public class Org extends BasicEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 机构代码
     */
    @TableId("org_id")
    private String orgId;

    /**
     * 机构名称
     */
    @TableField("org_name")
    private String orgName;

    /**
     * 上级机构
     */
    @TableField("parent_org_id")
    private String parentOrgId;

    /**
     * 机构所在地区代码
     */
    @TableField("area_code")
    private String areaCode;

    /**
     * 详细地址
     */
    @TableField("address")
    private String address;

    /**
     * 邮政编码
     */
    @TableField("post_code")
    private String postCode;

    /**
     * 联系人
     */
    @TableField("link_man")
    private String linkMan;

    /**
     * 联系方式
     */
    @TableField("link_mode")
    private String linkMode;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 启停状态[0:启用;1:停用]
     */
    @TableField("stop_status")
    private StopStatus stopStatus;

    /**
     * 停用原因
     */
    @TableField("stop_reason")
    private String stopReason;

    /**
     * 启停时间
     */
    @TableField("stop_time")
    private LocalDateTime stopTime;

    /**
     * 显示顺序
     */
    @TableField("order_num")
    private Integer orderNum;
}

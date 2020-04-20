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
@ApiModel(value="Org对象", description="机构表")
public class Org implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "机构代码")
    @TableId("org_id")
    private String orgId;

    @ApiModelProperty(value = "机构名称")
    @TableField("org_name")
    private String orgName;

    @ApiModelProperty(value = "上级机构")
    @TableField("parent_org_id")
    private String parentOrgId;

    @ApiModelProperty(value = "机构所在地区代码")
    @TableField("area_code")
    private String areaCode;

    @ApiModelProperty(value = "详细地址")
    @TableField("address")
    private String address;

    @ApiModelProperty(value = "邮政编码")
    @TableField("post_code")
    private String postCode;

    @ApiModelProperty(value = "联系人")
    @TableField("link_man")
    private String linkMan;

    @ApiModelProperty(value = "联系方式")
    @TableField("link_mode")
    private String linkMode;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "启停标志（0:停用,1:启用 用于数据是否有效）")
    @TableField("record_stop_flag")
    private String recordStopFlag;

    @ApiModelProperty(value = "停用原因")
    @TableField("stop_reason")
    private String stopReason;

    @ApiModelProperty(value = "启停时间")
    @TableField("record_stop_time")
    private LocalDateTime recordStopTime;

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

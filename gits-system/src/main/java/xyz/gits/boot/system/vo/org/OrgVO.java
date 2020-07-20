package xyz.gits.boot.system.vo.org;

import xyz.gits.boot.api.system.enums.StopStatus;
import xyz.gits.boot.common.core.basic.BasicVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 前端机构展示对象vo
 *
 * @author null
 * @date 2020/5/26 10:11
 */
@Data
@ApiModel("前端机构展示对象vo")
public class OrgVO extends BasicVO {

    @ApiModelProperty(value = "机构名称")
    private String orgId;

    @ApiModelProperty(value = "上级机构")
    private String parentOrgId;

    @ApiModelProperty(value = "机构名称")
    private String orgName;

    @ApiModelProperty(value = "机构所在地区代码")
    private String areaCode;

    @ApiModelProperty(value = "详细地址")
    private String address;

    @ApiModelProperty(value = "邮政编码")
    private String postCode;

    @ApiModelProperty(value = "联系人")
    private String linkMan;

    @ApiModelProperty(value = "联系方式")
    private String linkMode;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "启停状态[0:启用;1:停用]")
    private StopStatus stopStatus;

    @ApiModelProperty(value = "停用原因")
    private String stopReason;

    @ApiModelProperty(value = "启停时间")
    private LocalDateTime recordStopTime;

    @ApiModelProperty(value = "显示顺序")
    private Integer orderNum;

}

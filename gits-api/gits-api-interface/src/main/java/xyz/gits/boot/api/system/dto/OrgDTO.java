package xyz.gits.boot.api.system.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 机构传输对象
 *
 * @author songyinyin
 * @date 2020/6/13 下午 11:37
 */
@Data
@ApiModel(value = "机构传输对象")
public class OrgDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "机构代码不能为空")
    @ApiModelProperty(value = "机构代码")
    @Size(max = 16, message = "机构代码不能超过16")
    private String orgId;

    @ApiModelProperty(value = "机构名称")
    @NotBlank(message = "机构代码不能为空")
    @Size(max = 100, message = "机构名称不能超过100")
    private String orgName;

    @ApiModelProperty(value = "上级机构")
    private String parentOrgId;

    @ApiModelProperty(value = "排序值，值越大排序越靠前")
    private Integer orgOrder;

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
}

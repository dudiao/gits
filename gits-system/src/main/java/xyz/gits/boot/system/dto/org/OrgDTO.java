package xyz.gits.boot.system.dto.org;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.SpringSecurityCoreVersion;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 机构管理新增传输对象
 *
 * @author null
 * @date 2020/5/26 10:09
 */
@Data
@ApiModel(value = "机构管理新增传输对象")
public class OrgDTO  implements Serializable {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    @NotBlank(message = "机构代码不能为空")
    @ApiModelProperty(value = "机构代码", required = true)
    @Size(max = 31, message = "机构代码不能超过31")
    private String orgId;

    @ApiModelProperty(value = "机构名称", required = true)
    @NotBlank(message = "机构代码不能为空")
    @Size(max = 63, message = "机构名称不能超过63")
    private String orgName;

    @NotBlank(message = "机构父ID不能为空")
    @ApiModelProperty(value = "上级机构", required = true)
    @Size(max = 31, message = "机构代码不能超过31")
    private String parentOrgId;

    @NotBlank(message = "机构所在地区代码不能为空")
    @ApiModelProperty(value = "机构所在地区代码", required = true)
    @Size(max = 63, message = "机构所在地区代码不能超过63")
    private String areaCode;

    @NotBlank(message = "启停状态不能为空")
    @ApiModelProperty(value = "启停状态[0:启用;1:停用]", required = true)
    @Size(max = 1, message = "启停状态不能超过63")
    private String stopStatusCode;

    @ApiModelProperty(value = "停用原因")
    @Size(max = 255, message = "停用原因不能超过255")
    private String stopReason;

    @ApiModelProperty(value = "详细地址")
    @Size(max = 255, message = "详细地址不能超过255")
    private String address;

    @ApiModelProperty(value = "邮政编码")
    @Size(max = 23, message = "邮政编码不能超过23")
    private String postCode;

    @ApiModelProperty(value = "联系人")
    @Size(max = 31, message = "联系人不能超过31")
    private String linkMan;

    @ApiModelProperty(value = "联系方式")
    @Size(max = 31, message = "联系方式不能超过31")
    private String linkMode;

    @ApiModelProperty(value = "备注")
    @Size(max = 511, message = "备注不能超过511")
    private String remark;

    @NotBlank(message = "金融机构代码不能为空")
    @ApiModelProperty(value = "金融机构代码", required = true)
    @Size(max = 31, message = "金融机构代码不能超过31")
    private String prtOrgCode;

    @NotBlank(message = "跨境标示不能为空")
    @ApiModelProperty(value = "跨境标示[0:跨境;1:非跨境]", required = true)
    @Size(max = 1, message = "跨境标示不能超过1")
    private String crossBorderTypeCode;

    @ApiModelProperty(value = "现代化支付系统行号")
    @Size(max = 31, message = "现代化支付系统行号不能超过31")
    private String modernOrgId;

    @ApiModelProperty(value = "账号管理系统行号")
    @Size(max = 31, message = "账号管理系统行号不能超过31")
    private String accountOrgId;

    @ApiModelProperty(value = "其他类型网点代码")
    @Size(max = 31, message = "其他类型网点代码不能超过31")
    private String otherId;

    @ApiModelProperty(value = "有效状态[0:有效;1:无效]，用于导出人行报备，默认0", required = true)
    @Size(max = 1, message = "有效状态不能超过1")
    @NotBlank(message = "有效状态不能为空")
    private String statusCode;
}

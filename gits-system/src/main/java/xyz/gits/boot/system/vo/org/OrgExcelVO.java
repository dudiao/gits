package xyz.gits.boot.system.vo.org;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import xyz.gits.boot.common.core.excel.Excel;

import java.util.Date;

/**
 * 前端机构导出Excel对象vo
 *
 * @author null
 * @date 2020/5/26 10:11
 */
@Data
@ApiModel("前端机构导出Excel对象vo")
public class OrgExcelVO {

    /**
     * 现代化支付系统行号
     */
    @Excel(name = "现代化支付系统行号")
    private String modernOrgId;

    /**
     * 账号管理系统行号
     */
    @Excel(name = "账号管理系统行号")
    private String accountOrgId;

    /**
     * 金融机构代码
     */
    @Excel(name = "金融机构代码")
    private String rptOrgCode;

    /**
     * 其他类型网点代码
     */
    @Excel(name = "其他类型网点代码")
    private String otherId;

    /**
     * 机构名称
     */
    @Excel(name = "网点名称")
    private String orgName;

    /**
     * 行政区划代码
     */
    @Excel(name = "所在行政区划代码")
    private String areaCode;


    /**
     * 详细地址
     */
    @Excel(name = "网点地址")
    private String address;


    /**
     * 机构ID(内部机构代码)
     */
    @Excel(name = "内部机构代码")
    private String orgId;

    /**
     * 联系人
     */
    @Excel(name = "联系人")
    private String linkMan;

    /**
     * 联系方式
     */
    @Excel(name = "联系方式")
    private String linkMode;

    /**
     * 其他联系方式
     */
    @Excel(name = "其他联系方式")
    private String otherLinkMode;

    /**
     * 邮政编码
     */
    @Excel(name = "邮政编码")
    private String postCode;

    /**
     * 启停状态[0:启用;1:停用]
     */
    @Excel(name = "机构状态", readConverterExp = "0=启用,1=停用")
    private String stopStatusCode;


    /**
     * 机构分级[0:总行;1:分行;2:支行]
     */
    @Excel(name = "机构级别", readConverterExp = "0=总行,1=分行,2=支行")
    private Integer orgLevelCode;


    /**
     * 有效状态[0:有效;1:无效],用于导出人行报备，默认:0
     */
    @Excel(name = "是否有效", readConverterExp = "0=有效,1=无效")
    private String statusCode;

    /**
     * 显示顺序
     */
    @Excel(name = "显示顺序")
    private Integer orderNum;


    /**
     * 跨境标示[0:跨境;1:非跨境]
     */
    @Excel(name = "跨境标识", readConverterExp = "0=非跨境,1=跨境")
    private String crossBorderCode;

    /**
     * 创建者
     */
    @Excel(name = "创建者")
    private String createBy;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.EXPORT)
    private Date createTime;

    /**
     * 更新者
     */
    @Excel(name = "更新者")
    private String updateBy;

    /**
     * 更新时间
     */
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.EXPORT)
    private Date updateTime;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;
}
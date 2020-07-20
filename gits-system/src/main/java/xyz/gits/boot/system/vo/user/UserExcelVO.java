package xyz.gits.boot.system.vo.user;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import xyz.gits.boot.common.core.excel.Excel;

import java.util.Date;

/**
 * 前端用户导出展示对象
 *
 * @author null
 * @date 2020/5/18 17:34
 * @since
 */
@Data
@ApiModel(value = "前端用户导出展示对象")
public class UserExcelVO {

    /**
     * 用户ID（工号）
     */
    @Excel(name = "用户序号")
    private String userId;

    /**
     * 用户名
     */
    @Excel(name = "用户名，登录名【不能为中文】")
    private String userName;

    /**
     * 姓名
     */
    @Excel(name = "昵称、姓名")
    private String nickName;

    /**
     * 机构对象
     */
    @Excel(name = "机构名称")
    private String orgName;

    /**
     * 部门
     */
    @Excel(name = "部门")
    private String deptName;

    /**
     * 角色拼接
     */
    @Excel(name = "角色")
    private String roleStr;

    /**
     * 用户邮箱
     */
    @Excel(name = "用户邮箱")
    private String email;

    /**
     * 手机号码
     */
    @Excel(name = "手机号码")
    private String mobileNumber;

    /**
     * 联系电话
     */
    @Excel(name = "联系电话")
    private String phoneNumber;

    /**
     * 单位电话
     */
    @Excel(name = "单位电话")
    private String workTelephone;

    /**
     * 通讯地址
     */
    @Excel(name = "通讯地址")
    private String address;


    /**
     * 邮政编码
     */
    @Excel(name = "邮政编码")
    private String postalCode;

    /**
     * 微信号
     */
    @Excel(name = "微信号")
    private String weiChat;

    /**
     * 帐号启用状态（1正常 0停用）
     */
    @Excel(name = "帐号状态", readConverterExp = "1=正常,0=停用")
    private String stopFlag;

    /**
     * 账号锁定状态
     */
    @Excel(name = "帐号状态", readConverterExp = "1=锁定,0=未锁定")
    private String pwdLockFlag;

    /**
     * 最后登陆IP
     */
    @Excel(name = "最后登陆IP", type = Excel.Type.EXPORT)
    private String loginIp;

    /**
     * 最后登陆时间
     */
    @Excel(name = "最后登陆时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.EXPORT)
    private Date loginDate;


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

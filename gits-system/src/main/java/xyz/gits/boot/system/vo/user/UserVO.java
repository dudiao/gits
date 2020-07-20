package xyz.gits.boot.system.vo.user;

import xyz.gits.boot.api.system.enums.LockStatus;
import xyz.gits.boot.api.system.enums.Sex;
import xyz.gits.boot.api.system.enums.StopStatus;
import xyz.gits.boot.common.core.basic.BasicVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author: null
 * @date: 2020/6/5 10:41
 */
@Data
@ApiModel(value = "前端用户详情展示")
public class UserVO extends BasicVO {

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "所属机构代码")
    private String orgId;

    @ApiModelProperty(value = "所属机构名称")
    private String orgName;

    @ApiModelProperty(value = "用户名，登录名【不能为中文】")
    private String userName;

    @ApiModelProperty(value = "昵称、姓名")
    private String nickName;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "性别[0:女;1:男]")
    private Sex sex;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "微信号")
    private String wechat;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "密码修改时间")
    private LocalDateTime pwdUpdateTime;

    @ApiModelProperty(value = "启停状态[0:启用;1:停用]")
    private StopStatus stopStatus;

    @ApiModelProperty(value = "停用时间")
    private LocalDateTime stopTime;

    @ApiModelProperty(value = "停用原因")
    private String stopReason;

    @ApiModelProperty(value = "密码错误次数")
    private BigDecimal pwdErrorNumber;

    @ApiModelProperty(value = "密码锁定状态[0:未锁定;1:已锁定]")
    private LockStatus pwdLockStatus;

    @ApiModelProperty(value = "用户锁定时间")
    private LocalDateTime lockTime;

}

package xyz.gits.boot.api.system.entity;

import java.math.BigDecimal;
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
import xyz.gits.boot.api.system.enums.LockFlag;
import xyz.gits.boot.api.system.enums.StopFlag;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author songyinyin
 * @date 2020-02-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("system_user")
@ApiModel(value="User对象", description="用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    @TableId("user_id")
    private String userId;

    @ApiModelProperty(value = "用户名，登录名")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty(value = "昵称")
    @TableField("nick_name")
    private String nickName;

    @ApiModelProperty(value = "头像")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty(value = "用户密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "所属机构代码")
    @TableField("org_id")
    private String orgId;

    @ApiModelProperty(value = "邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "手机号码")
    @TableField("mobile")
    private String mobile;

    @ApiModelProperty(value = "微信号")
    @TableField("wechat")
    private String wechat;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "密码修改时间")
    @TableField("pwd_update_time")
    private LocalDateTime pwdUpdateTime;

    @ApiModelProperty(value = "停用标志（0:停用，1:启用）")
    @TableField("stop_flag")
    private StopFlag stopFlag;

    @ApiModelProperty(value = "停用时间")
    @TableField("stop_time")
    private LocalDateTime stopTime;

    @ApiModelProperty(value = "停用原因")
    @TableField("stop_reason")
    private String stopReason;

    @ApiModelProperty(value = "密码错误次数")
    @TableField("pwd_error_number")
    private BigDecimal pwdErrorNumber;

    @ApiModelProperty(value = "密码锁定标志（0-未锁定，1-密码错误锁定）")
    @TableField("pwd_lock_flag")
    private LockFlag pwdLockFlag;

    @ApiModelProperty(value = "用户锁定时间")
    @TableField("lock_time")
    private LocalDateTime lockTime;

    @ApiModelProperty(value = "编辑用户")
    @TableField("update_user_id")
    private String updateUserId;

    @ApiModelProperty(value = "编辑时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "创建用户")
    @TableField("create_user_id")
    private String createUserId;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "最近成功登陆时间")
    @TableField("login_success_time")
    private LocalDateTime loginSuccessTime;

    @ApiModelProperty(value = "gitee用户id")
    @TableField("gitee_id")
    private String giteeId;


}

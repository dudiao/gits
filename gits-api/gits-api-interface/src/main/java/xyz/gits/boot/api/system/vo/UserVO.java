package xyz.gits.boot.api.system.vo;

import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import xyz.gits.boot.api.system.enums.LockFlag;
import xyz.gits.boot.api.system.enums.StopFlag;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 前端用户展示对象
 *
 * @author songyinyin
 * @date 2020/3/26 下午 09:18
 */
@Data
@ApiModel(value = "前端用户展示对象")
public class UserVO implements Serializable {

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "用户名，登录名")
    private String userName;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "所属机构代码")
    private String orgId;

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

    @ApiModelProperty(value = "停用标志（0:停用，1:启用）")
    private StopFlag stopFlag;

    @ApiModelProperty(value = "停用时间")
    private LocalDateTime stopTime;

    @ApiModelProperty(value = "停用原因")
    private String stopReason;

    @ApiModelProperty(value = "密码错误次数")
    private BigDecimal pwdErrorNumber;

    @ApiModelProperty(value = "密码锁定标志（0-未锁定，1-密码错误锁定）")
    private LockFlag pwdLockFlag;

    @ApiModelProperty(value = "用户锁定时间")
    private LocalDateTime lockTime;

    @ApiModelProperty(value = "编辑用户")
    private String updateUserId;

    @ApiModelProperty(value = "编辑时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "创建用户")
    private String createUserId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "最近成功登陆时间")
    private LocalDateTime loginSuccessTime;

    @ApiModelProperty(value = "gitee用户id")
    private String giteeId;

    /**
     * sessionId
     */
    @ApiModelProperty(value = "sessionId")
    private String sessionId;

    /**
     * session   登录ip
     */
    @ApiModelProperty(value = "登录ip")
    private String loginIp;
}

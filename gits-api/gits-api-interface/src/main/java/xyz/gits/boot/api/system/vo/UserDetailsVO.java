package xyz.gits.boot.api.system.vo;

import lombok.Data;
import xyz.gits.boot.api.system.enums.LockStatus;
import xyz.gits.boot.api.system.enums.Sex;
import xyz.gits.boot.api.system.enums.StopStatus;
import xyz.gits.boot.common.core.constants.SecurityConstant;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户对象
 *
 * @author songyinyin
 * @date 2020/3/26 下午 09:18
 */
@Data
public class UserDetailsVO implements Serializable {

    private static final long serialVersionUID = SecurityConstant.SERIAL_VERSION_UID;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 所属机构代码
     */
    private String orgId;

    /**
     * 用户名，登录名【不能为中文】
     */
    private String userName;

    /**
     * sessionId
     */
    private String sessionId;

    /**
     * 昵称、姓名
     */
    private String nickName;

    /**
     * session   登录ip
     */
    private String loginIp;


    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别[0:女;1:男]
     */
    private Sex sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 微信号
     */
    private String wechat;

    /**
     * 备注
     */
    private String remark;

    /**
     * 密码修改时间
     */
    private LocalDateTime pwdUpdateTime;

    /**
     * 启停状态[0:启用;1:停用]
     */
    private StopStatus stopStatus;

    /**
     * 停用时间
     */
    private LocalDateTime stopTime;

    /**
     * 停用原因
     */
    private String stopReason;

    /**
     * 密码错误次数
     */
    private BigDecimal pwdErrorNumber;

    /**
     * 密码锁定状态[0:未锁定;1:已锁定]
     */
    private LockStatus pwdLockStatus;

    /**
     * 用户锁定时间
     */
    private LocalDateTime lockTime;

    /**
     * 最近成功登陆时间
     */
    private LocalDateTime loginSuccessTime;

    /**
     * 编辑用户
     */
    private String updateUserId;

    /**
     * 编辑时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建用户
     */
    private String createUserId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}

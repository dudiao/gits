package xyz.gits.boot.api.system.vo;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import xyz.gits.boot.common.core.enums.LoginType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 登录用户
 *
 * @author dingmingyang
 * @date 2020/06/03/15:53
 */
@Data
public class LoginUser<T extends UserVO> implements Serializable {

    /**
     * 用户
     */
    private T user;

    /**
     * 权限标识集合
     */
    private Set<String> permissions;

    /**
     * 角色集合
     */
    private Set<String> roles;

    /**
     * 获取当前用户菜单集合
     */
    private Set<String> resources;

    /**
     * 登录ip
     */
    private String loginIp;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    /**
     * 登陆类型
     */
    private LoginType loginType;

    /**
     * 用户密码
     */
    private String password;

    public LoginUser() {
    }

    public LoginUser(T user, String loginIp, LocalDateTime loginTime, LoginType loginType, String password) {
        this.user = user;
        this.loginIp = loginIp;
        this.loginTime = loginTime;
        this.loginType = loginType;
        this.password = password;
    }

    public LoginUser(T user, String loginIp, LocalDateTime loginTime, String loginType) {
        this.user = user;
        this.loginIp = loginIp;
        this.loginTime = loginTime;
        this.loginType = LoginType.valueOf(loginType);
    }

    /**
     * 是否为管理员
     *
     * @author songyinyin
     * @date 2020/5/12
     */
    public boolean isAdmin() {
        return StrUtil.equals(this.getUser().getUserId(), "1");
    }
}
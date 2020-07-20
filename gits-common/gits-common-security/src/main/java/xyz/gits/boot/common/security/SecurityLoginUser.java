package xyz.gits.boot.common.security;

import cn.hutool.core.util.ObjectUtil;
import lombok.Data;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;
import xyz.gits.boot.api.system.enums.LockStatus;
import xyz.gits.boot.api.system.enums.StopStatus;
import xyz.gits.boot.api.system.vo.LoginUser;
import xyz.gits.boot.api.system.vo.UserDetailsVO;

import java.util.Collection;

/**
 * Security 扩展用户信息
 *
 * @author songyinyin
 * @date 2020/3/14 下午 05:29
 */
@Data
public class SecurityLoginUser<T extends UserDetailsVO> implements UserDetails, CredentialsContainer {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private LoginUser<T> loginUser;

    public SecurityLoginUser(LoginUser<T> user) {
        this.loginUser = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return loginUser.getPassword();
    }

    @Override
    public String getUsername() {
        return loginUser.getUser().getUserName();
    }

    /**
     * 账户是否未过期，过期无法验证
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 指定用户是否解锁，锁定的用户无法进行身份验证
     * <p>
     * 密码锁定
     * </p>
     */
    @Override
    public boolean isAccountNonLocked() {
        return ObjectUtil.equal(loginUser.getUser().getPwdLockStatus(), LockStatus.UN_LOCK);
    }

    /**
     * 指示是否已过期的用户的凭据(密码)，过期的凭据防止认证
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 用户是否被启用或禁用。禁用的用户无法进行身份验证。
     */
    @Override
    public boolean isEnabled() {
        return ObjectUtil.equal(loginUser.getUser().getStopStatus(), StopStatus.ENABLE);
    }

    /**
     * 认证完成后，擦除密码
     */
    @Override
    public void eraseCredentials() {
        loginUser.setPassword(null);
    }
}

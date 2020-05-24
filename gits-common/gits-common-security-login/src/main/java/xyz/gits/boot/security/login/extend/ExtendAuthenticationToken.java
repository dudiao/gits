package xyz.gits.boot.security.login.extend;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * @author songyinyin
 * @date 2020/5/4 下午 10:14
 */
public class ExtendAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    // ~ Instance fields
    // ================================================================================================

    private String extendType;
    private String extendKey;
    private final Object principal;
    private Object credentials;

    // ~ Constructors
    // ===================================================================================================

    /**
     * 认证前使用
     */
    public ExtendAuthenticationToken(String extendKey, String extendType, Object credentials) {
        super(null);
        this.extendKey = extendKey;
        this.extendType = extendType;
        this.principal = extendKey;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    /**
     * justauth 使用
     */
    public ExtendAuthenticationToken(Object authUser) {
        super(null);
        this.principal = authUser;
        setAuthenticated(false);
    }

    /**
     * 认证成功之后使用
     */
    public ExtendAuthenticationToken(String extendKey, String extendType, Object principal,
                                     Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.extendKey = extendKey;
        this.extendType = extendType;
        this.principal = principal;
        super.setAuthenticated(true); // must use super, as we override
    }

    // ~ Methods
    // ========================================================================================================


    public String getExtendType() {
        return extendType;
    }

    public String getExtendKey() {
        return extendKey;
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        credentials = null;
    }
}

package xyz.gits.boot.security.login.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import xyz.gits.boot.security.login.extend.ExtendAuthenticationToken;
import xyz.gits.boot.security.login.extend.ExtendUserDetailsService;

/**
 * 第三方登录获取用户信息的默认实现，直接使用了<tt>UserDetailsService#loadUserByUsername(java.lang.String)</tt>
 *
 * @author songyinyin
 * @date 2020/5/5 下午 05:14
 */
@Slf4j
public class ExtendUserDetailsServiceImpl implements ExtendUserDetailsService {

    @Autowired
    private DefaultUserDetailsService userDetailsService;

    /**
     * 扩展第三方登录
     */
    @Override
    public UserDetails loadUserByExtendKey(String extendKey, ExtendAuthenticationToken token) throws UsernameNotFoundException {
        log.info("extend, type={}", token.getExtendType());
        return userDetailsService.loadUserByUsername(extendKey);
    }
}

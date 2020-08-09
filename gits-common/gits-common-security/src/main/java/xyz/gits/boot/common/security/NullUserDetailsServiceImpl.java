package xyz.gits.boot.common.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 空实现，防止security默认实现 UserDetailsServiceAutoConfiguration
 *
 * @author songyinyin
 * @date 2020/8/9 上午 12:38
 */
public class NullUserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}

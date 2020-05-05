package xyz.gits.boot.security.login.extend;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author songyinyin
 * @date 2020/5/5 上午 12:05
 */
public interface ExtendUserDetailsService {

    UserDetails loadUserByExtendKey(ExtendAuthenticationToken token) throws UsernameNotFoundException;
}

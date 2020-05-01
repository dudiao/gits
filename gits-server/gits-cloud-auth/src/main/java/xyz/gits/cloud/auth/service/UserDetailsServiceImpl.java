package xyz.gits.cloud.auth.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xyz.gits.boot.api.system.service.SystemService;
import xyz.gits.boot.api.system.vo.UserVO;
import xyz.gits.boot.common.core.utils.IpUtils;
import xyz.gits.boot.common.core.utils.ServletUtils;
import xyz.gits.boot.common.security.LoginUser;

import java.time.LocalDateTime;

/**
 * @author songyinyin
 * @date 2020/2/19 下午 10:18
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SystemService systemService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StrUtil.isBlank(username)) {
            log.info("登录用户：{} 不存在", username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        }

        // 查出密码
        UserVO userVO = systemService.loadUserByUsername(username);
        if (ObjectUtil.isNull(userVO) || StrUtil.isBlank(userVO.getUserId())) {
            log.info("登录用户：{} 不存在", username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        }
        return new LoginUser(userVO, IpUtils.getIpAddr(ServletUtils.getRequest()), LocalDateTime.now());
    }
}

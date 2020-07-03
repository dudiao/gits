package xyz.gits.boot.security.login.service;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import xyz.gits.boot.api.system.service.SystemService;
import xyz.gits.boot.api.system.vo.LoginUser;
import xyz.gits.boot.api.system.vo.UserVO;
import xyz.gits.boot.common.core.enums.LoginType;
import xyz.gits.boot.common.core.response.ResponseCode;
import xyz.gits.boot.common.core.response.RestResponse;
import xyz.gits.boot.common.core.utils.IpUtils;
import xyz.gits.boot.common.core.utils.ServletUtils;
import xyz.gits.boot.common.security.SecurityLoginUser;

import java.time.LocalDateTime;

/**
 * @author songyinyin
 * @date 2020/2/19 下午 10:18
 */
@Slf4j
public class DefaultUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SystemService systemService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StrUtil.isBlank(username)) {
            log.info("登录用户：{} 不存在", username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        }

        // 查出密码
        RestResponse<LoginUser<UserVO>> response = systemService.loadUserByUsername(username);
        if (ResponseCode.USER_NOT_EXIST.getCode() == response.getCode()) {
            log.info("登录用户：{} 不存在", username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        }
        LoginUser<UserVO> loginUser = response.getData();
        loginUser.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        loginUser.setLoginTime(LocalDateTime.now());
        loginUser.setLoginType(LoginType.PASSWORD);
        return new SecurityLoginUser<>(loginUser);
    }

}

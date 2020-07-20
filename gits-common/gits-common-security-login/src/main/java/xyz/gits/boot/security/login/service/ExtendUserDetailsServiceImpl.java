package xyz.gits.boot.security.login.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.config.AuthDefaultSource;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import xyz.gits.boot.api.system.dto.UserAddDTO;
import xyz.gits.boot.api.system.enums.LoginType;
import xyz.gits.boot.api.system.service.SystemService;
import xyz.gits.boot.api.system.vo.LoginUser;
import xyz.gits.boot.api.system.vo.UserDetailsVO;
import xyz.gits.boot.common.core.response.ResponseCode;
import xyz.gits.boot.common.core.response.RestResponse;
import xyz.gits.boot.common.core.utils.IpUtils;
import xyz.gits.boot.common.core.utils.ServletUtils;
import xyz.gits.boot.common.security.SecurityLoginUser;
import xyz.gits.boot.security.login.extend.ExtendAuthenticationToken;
import xyz.gits.boot.security.login.extend.ExtendUserDetailsService;

import java.time.LocalDateTime;

/**
 * 第三方登录获取用户信息的默认实现，兼容了 justauth。
 * <p>有特殊需求可以继承 ExtendUserDetailsService，并使其子类成为 spring bean</p>
 *
 * @author songyinyin
 * @date 2020/5/5 下午 05:14
 */
@Slf4j
public class ExtendUserDetailsServiceImpl implements ExtendUserDetailsService {

    @Autowired
    private DefaultUserDetailsServiceImpl userDetailsService;
    @Autowired
    private SystemService systemService;

    /**
     * 扩展第三方登录
     */
    @Override
    public UserDetails loadUserByExtendKey(ExtendAuthenticationToken token) throws UsernameNotFoundException {
        if (!(ObjectUtil.isNotEmpty(token.getPrincipal()) && token.getPrincipal() instanceof AuthUser)) {
            log.info("extend, type={}", token.getExtendType());
            return userDetailsService.loadUserByUsername(token.getExtendKey());
        }

        AuthUser authUser = (AuthUser) token.getPrincipal();
        // 1. 根据 gitee 唯一id 查找用户信息
        /**
         * 这里要求 user 表中有 authUser.getSource()+'_id' 字段（小写，如 gitee_id），authUser.getSource()的取值见 {@link AuthDefaultSource}
         */
        RestResponse<LoginUser<UserDetailsVO>> loadResponse = systemService.loadUserByBiz(authUser.getSource().toLowerCase() + "_id", authUser.getUuid());

        // 2. 用户存在 --> 返回 UserDetails
        if (loadResponse.isSuccess()) {
            LoginUser<UserDetailsVO> loginUser = loadResponse.getData();
            loginUser.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
            loginUser.setLoginTime(LocalDateTime.now());
            loginUser.setLoginType(LoginType.EXTEND);
            return new SecurityLoginUser<>(loginUser);
        }
        // 3. 用户不存在 --> 新增（注册）用户，之后返回 UserDetails
        if (ResponseCode.USER_NOT_EXIST.getCode() == loadResponse.getCode()) {
            UserAddDTO user = new UserAddDTO();
            user.setUserName(authUser.getUsername());
            user.setNickName(authUser.getNickname());
            user.setAvatar(authUser.getAvatar());
            user.setRemark(authUser.getRemark());
            if (StrUtil.equalsIgnoreCase(authUser.getSource(), AuthDefaultSource.GITEE.getName())) {
                user.setGiteeId(authUser.getUuid());
            }
            RestResponse<LoginUser<UserDetailsVO>> response = systemService.registerUser(user);
            if (response.isSuccess()) {
                LoginUser<UserDetailsVO> loginUser = loadResponse.getData();
                loginUser.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
                loginUser.setLoginTime(LocalDateTime.now());
                loginUser.setLoginType(LoginType.valueOf(authUser.getSource()));
                return new SecurityLoginUser<>(loginUser);
            }
            log.error("[扩展登录] - 注册用户失败：{}", response);
            return null;
        }

        log.error("[扩展登录] - 登录失败：{}", loadResponse);
        return null;
    }

}

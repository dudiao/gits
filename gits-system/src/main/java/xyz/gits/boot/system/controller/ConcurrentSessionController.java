package xyz.gits.boot.system.controller;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.gits.boot.api.system.vo.LoginUser;
import xyz.gits.boot.api.system.vo.UserDetailsVO;
import xyz.gits.boot.common.core.response.RestResponse;
import xyz.gits.boot.common.core.utils.BeanUtils;
import xyz.gits.boot.common.security.SecurityLoginUser;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 在线用户管理
 *
 * @author songyinyin
 * @date 2020/2/25 下午 11:21
 */
@Api(tags = "在线用户")
@RestController
public class ConcurrentSessionController {

    @Autowired
    private SessionRegistry sessionRegistry;

    @ApiOperation("在线用户列表")
    @GetMapping("/session/list")
    public RestResponse<List<LoginUser>> getCurrentUser() {
        List<Object> list = sessionRegistry.getAllPrincipals();

        List<LoginUser> userVOList = list.stream().filter(e -> e instanceof SecurityLoginUser).map(e -> {
            SecurityLoginUser securityLoginUser = (SecurityLoginUser) e;

            LoginUser loginUser = securityLoginUser.getLoginUser();

            return loginUser;
        }).collect(Collectors.toList());

        return RestResponse.success(userVOList);
    }

    @ApiOperation("用户在线详情")
    @GetMapping("/session/detail")
    public RestResponse detail(@ApiParam(name = "userId", value = "用户Id", required = true)
                               @RequestParam("userId") String userId) {
        // 获取session中所有的用户信息
        List<Object> list = sessionRegistry.getAllPrincipals();

        for (Object principal : list) {
            if (principal instanceof SecurityLoginUser) {
                SecurityLoginUser securityLoginUser = (SecurityLoginUser) principal;
                if (StrUtil.equals(userId, securityLoginUser.getLoginUser().getUser().getUserId())) {
                    // 获取该用户没有过期的会话
                    List<SessionInformation> sessionsInfo = sessionRegistry.getAllSessions(securityLoginUser, false);

                    List<UserDetailsVO> userDetailsVOList = sessionsInfo.stream().map(e -> {
                        SecurityLoginUser sessionUser = (SecurityLoginUser) e.getPrincipal();
                        UserDetailsVO userDetailsVO = new UserDetailsVO();
                        BeanUtils.copyPropertiesIgnoreNull(sessionUser, userDetailsVO);
                        userDetailsVO.setLoginSuccessTime(sessionUser.getLoginUser().getLoginTime());
                        userDetailsVO.setLoginIp(sessionUser.getLoginUser().getLoginIp());
                        userDetailsVO.setSessionId(e.getSessionId());
                        return userDetailsVO;
                    }).collect(Collectors.toList());
                    return RestResponse.success(userDetailsVOList);
                }
            }
        }

        return RestResponse.success();
    }

    @ApiOperation("踢出用户")
    @GetMapping("/session/kick")
    public RestResponse kick(@ApiParam(name = "sessionId", value = "用户会话id", required = true)
                             @RequestParam("sessionId") String sessionId) {
        sessionRegistry.refreshLastRequest(sessionId);
        return RestResponse.success();
    }


}

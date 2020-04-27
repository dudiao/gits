package xyz.gits.boot.system.controller;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.gits.boot.api.system.vo.UserVO;
import xyz.gits.boot.common.core.response.RestResponse;
import xyz.gits.boot.common.core.utils.BeanUtils;
import xyz.gits.boot.common.security.LoginUser;

import javax.validation.constraints.NotNull;
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
@RequestMapping("/session")
public class ConcurrentSessionController {

    @Autowired
    private SessionRegistry sessionRegistry;

    @ApiOperation("在线用户列表")
    @GetMapping("/list")
    public RestResponse getCurrentUser() {
        List<Object> list = sessionRegistry.getAllPrincipals();

        List<UserVO> userVOList = list.stream().filter(e -> e instanceof LoginUser).map(e -> {
            LoginUser loginUser = (LoginUser) e;
            UserVO userVO = new UserVO();
            BeanUtils.copyPropertiesIgnoreNull(loginUser, userVO);
            return userVO;
        }).collect(Collectors.toList());

        return RestResponse.success(userVOList);
    }

    @ApiOperation("用户在线详情")
    @GetMapping("/detail")
    public RestResponse detail(@ApiParam(name = "userId", value = "用户Id")
                               @RequestParam("userId")
                               @NotNull(message = "用户id不能为空") String userId) {
        // 获取session中所有的用户信息
        List<Object> list = sessionRegistry.getAllPrincipals();

        for (Object principal : list) {
            if (principal instanceof LoginUser) {
                LoginUser loginUser = (LoginUser) principal;
                if (StrUtil.equals(userId, loginUser.getUser().getUserId())) {
                    // 获取该用户没有过期的会话
                    List<SessionInformation> sessionsInfo = sessionRegistry.getAllSessions(loginUser, false);

                    List<UserVO> userVOList = sessionsInfo.stream().map(e -> {
                        LoginUser sessionUser = (LoginUser) e.getPrincipal();
                        UserVO userVO = new UserVO();
                        BeanUtils.copyPropertiesIgnoreNull(sessionUser, userVO);
                        userVO.setLoginSuccessTime(sessionUser.getLoginTime());
                        userVO.setLoginIp(sessionUser.getLoginIp());
                        userVO.setSessionId(e.getSessionId());
                        return userVO;
                    }).collect(Collectors.toList());
                    return RestResponse.success(userVOList);
                }
            }
        }

        return RestResponse.success();
    }

    @ApiOperation("踢出用户")
    @GetMapping("/kick")
    public RestResponse kick(@ApiParam(name = "sessionId", value = "用户会话id")
                             @RequestParam("sessionId")
                             @NotNull(message = "用户会话id不能为空") String sessionId) {
        sessionRegistry.refreshLastRequest(sessionId);
        return RestResponse.success();
    }


}

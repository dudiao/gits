package xyz.gits.cloud.system.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.gits.boot.api.system.dto.UserAddDTO;
import xyz.gits.boot.api.system.vo.LoginUser;
import xyz.gits.boot.api.system.vo.UserDetailsVO;
import xyz.gits.boot.common.core.response.RestResponse;
import xyz.gits.boot.system.controller.UserController;

/**
 * Feign调用接口
 *
 * @author songyinyin
 * @date 2020/5/3 上午 12:38
 */
@RestController
public class CrsUserController extends UserController {

    @GetMapping("/user/info/{userName}")
    @ApiOperation(value = "查看用户详情（内部接口调用）")
    public RestResponse<LoginUser<UserDetailsVO>> info(@ApiParam(name = "userName", value = "用户名") @PathVariable("userName") String userName) {
        // TODO 需要鉴权
        return systemService.loadUserByUsername(userName);
    }

    @PostMapping("/user/register")
    @ApiOperation(value = "注册用户")
    public RestResponse<LoginUser<UserDetailsVO>> register(@Validated @RequestBody UserAddDTO dto) {
        LoginUser<UserDetailsVO> loginUser = userService.saveUser(dto);
        return RestResponse.success(loginUser);
    }

    @GetMapping("/user/find")
    @ApiOperation(value = "查找用户")
    public RestResponse<LoginUser<UserDetailsVO>> find(String fieldName, String value) {
        return systemService.loadUserByBiz(fieldName, value);
    }
}

package xyz.gits.cloud.system.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.gits.boot.api.system.dto.UserSaveDTO;
import xyz.gits.boot.api.system.vo.LoginUser;
import xyz.gits.boot.system.controller.UserController;

/**
 * @author songyinyin
 * @date 2020/5/3 上午 12:38
 */
@RestController
public class CrsUserController extends UserController {

    @GetMapping("/user/info/{userName}")
    @ApiOperation(value = "查看用户详情（内部接口调用）")
    public LoginUser info(@ApiParam(name = "userName", value = "用户名") @PathVariable("userName") String userName) {
        // TODO 需要鉴权
        LoginUser loginUser = systemService.loadUserByUsername(userName);
        return loginUser;
    }

    @PostMapping("/user/register")
    @ApiOperation(value = "注册用户")
    public LoginUser register(@Validated @RequestBody UserSaveDTO dto) {
        LoginUser loginUser = userService.saveUser(dto);
        return loginUser;
    }

    @GetMapping("/user/find")
    @ApiOperation(value = "查找用户")
    public LoginUser find(String fieldName, String value) {
        LoginUser loginUser = systemService.loadUserByBiz(fieldName, value);
        return loginUser;
    }
}

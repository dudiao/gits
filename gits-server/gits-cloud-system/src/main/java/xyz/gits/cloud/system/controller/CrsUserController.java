package xyz.gits.cloud.system.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.gits.boot.api.system.dto.UserDTO;
import xyz.gits.boot.api.system.vo.UserVO;
import xyz.gits.boot.common.core.validate.CreateGroup;
import xyz.gits.boot.system.controller.UserController;

/**
 * @author songyinyin
 * @date 2020/5/3 上午 12:38
 */
@RestController
public class CrsUserController extends UserController {

    @GetMapping("/user/info/{userName}")
    @ApiOperation(value = "查看用户详情（内部接口调用）")
    public UserVO info(@ApiParam(name = "userName", value = "用户名") @PathVariable("userName") String userName) {
        // TODO 需要鉴权
        UserVO userVO = systemService.loadUserByUsername(userName);
        return userVO;
    }

    @PostMapping("/user/register")
    @ApiOperation(value = "注册用户")
    public UserVO register(@Validated(CreateGroup.class) @RequestBody UserDTO userDTO) {
        UserVO userVO = userService.saveUser(userDTO);
        return userVO;
    }

    @GetMapping("/user/find")
    @ApiOperation(value = "查找用户")
    public UserVO find(String fieldName, String value) {
        UserVO userVO = systemService.loadUserByBiz(fieldName, value);
        return userVO;
    }
}

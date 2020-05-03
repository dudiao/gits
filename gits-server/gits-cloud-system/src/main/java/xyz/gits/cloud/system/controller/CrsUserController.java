package xyz.gits.cloud.system.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import xyz.gits.boot.api.system.vo.UserVO;
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
}

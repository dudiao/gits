package xyz.gits.boot.api.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import xyz.gits.boot.api.system.dto.UserSaveDTO;
import xyz.gits.boot.api.system.service.SystemService;
import xyz.gits.boot.api.system.vo.LoginUser;
import xyz.gits.boot.api.system.vo.UserVO;
import xyz.gits.boot.common.core.response.RestResponse;

/**
 * 预留实现：Feign服务间调用
 *
 * @author songyinyin
 * @date 2020/4/4 下午 09:25
 */
@FeignClient(name = "gits-system", url = "${gits.system-url:}")
public interface SystemFeign extends SystemService {

    @Override
    @GetMapping("/user/info/{userName}")
    RestResponse<LoginUser<UserVO>> loadUserByUsername(@PathVariable("userName") String userName);

    @Override
    @GetMapping("/user/find")
    RestResponse<LoginUser<UserVO>> loadUserByBiz(@RequestParam("fieldName") String fieldName, @RequestParam("value") String value);

    @Override
    @PostMapping("/user/register")
    RestResponse<LoginUser<UserVO>> registerUser(@RequestBody UserSaveDTO user);
}

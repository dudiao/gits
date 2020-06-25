package xyz.gits.boot.api.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import xyz.gits.boot.api.system.dto.UserSaveDTO;
import xyz.gits.boot.api.system.service.SystemService;
import xyz.gits.boot.api.system.vo.LoginUser;

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
    LoginUser loadUserByUsername(@PathVariable("userName") String userName);

    @Override
    @GetMapping("/user/find")
    LoginUser loadUserByBiz(@RequestParam("fieldName") String fieldName, @RequestParam("value") String value);

    @Override
    @PostMapping("/user/register")
    LoginUser registerUser(@RequestBody UserSaveDTO user);
}

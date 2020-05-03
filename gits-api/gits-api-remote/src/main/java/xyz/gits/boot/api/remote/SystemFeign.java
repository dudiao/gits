package xyz.gits.boot.api.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xyz.gits.boot.api.system.service.SystemService;
import xyz.gits.boot.api.system.vo.UserVO;

/**
 * 预留实现：Feign服务间调用
 *
 * @author songyinyin
 * @date 2020/4/4 下午 09:25
 */
@FeignClient(name = "gits-system")
public interface SystemFeign extends SystemService {

    @Override
    @GetMapping("/user/info/{userName}")
    UserVO loadUserByUsername(@PathVariable("userName") String userName);
}

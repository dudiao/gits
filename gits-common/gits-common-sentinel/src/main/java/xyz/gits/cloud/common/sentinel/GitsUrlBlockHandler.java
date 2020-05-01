package xyz.gits.cloud.common.sentinel;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import xyz.gits.boot.common.core.response.ResponseCode;
import xyz.gits.boot.common.core.response.RestResponse;
import xyz.gits.boot.common.core.utils.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author songyinyin
 * @date 2020/4/30 下午 11:45
 */
@Slf4j
public class GitsUrlBlockHandler implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
        log.error("sentinel 降级 资源名称{}", e.getRule().getResource(), e);
        ServletUtils.render(request, response, RestResponse.build(ResponseCode.TOO_MANY_REQUESTS, e.getMessage()));
    }
}

package xyz.gits.cloud.gateway.filter;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 演示环境过滤处理
 *
 * @author songyinyin
 * @date 2020/5/1 上午 01:02
 */
@Slf4j
@Component
public class PreviewGatewayFilter extends AbstractGatewayFilterFactory {

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            // GET，直接向下执行 TODO 登录接口放行
            if (StrUtil.equalsIgnoreCase(request.getMethodValue(), HttpMethod.GET.name())) {
                return chain.filter(exchange);
            }

            log.warn("演示环境不能操作-> {},{}", request.getMethodValue(), request.getURI().getPath());
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.LOCKED);
            return response.setComplete();
        };
    }
}

package xyz.gits.cloud.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xyz.gits.boot.common.security.SecurityConstant;

/**
 * 请求头清洗，移除 form。配合服务间调用 {@link xyz.gits.boot.common.security.annotation.Inner}
 *
 * @author songyinyin
 * @date 2020/8/6 下午 11:28
 */
@Component
public class RequestCleanFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest().mutate()
            .headers(httpHeaders -> httpHeaders.remove(SecurityConstant.FROM)).build();

        ServerHttpRequest newRequest = request.mutate().build();
        return chain.filter(exchange.mutate().request(newRequest).build());
    }


    @Override
    public int getOrder() {
        return -1000;
    }
}

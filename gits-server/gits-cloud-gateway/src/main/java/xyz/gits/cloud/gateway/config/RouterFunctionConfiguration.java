package xyz.gits.cloud.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import xyz.gits.cloud.gateway.hander.SwaggerResourceHandler;
import xyz.gits.cloud.gateway.hander.SwaggerSecurityHandler;
import xyz.gits.cloud.gateway.hander.SwaggerUiHandler;

/**
 * @author songyinyin
 * @date 2020/5/1 下午 03:30
 */
@Configuration
public class RouterFunctionConfiguration {

    @Autowired
    private SwaggerResourceHandler swaggerResourceHandler;
    @Autowired
    private SwaggerSecurityHandler swaggerSecurityHandler;
    @Autowired
    private SwaggerUiHandler swaggerUiHandler;

    @Bean
    public RouterFunction routerFunction() {
        return RouterFunctions.route(RequestPredicates.GET("/swagger-resources")
                        .and(RequestPredicates.accept(MediaType.ALL)), swaggerResourceHandler)
                .andRoute(RequestPredicates.GET("/swagger-resources/configuration/ui")
                        .and(RequestPredicates.accept(MediaType.ALL)), swaggerUiHandler)
                .andRoute(RequestPredicates.GET("/swagger-resources/configuration/security")
                        .and(RequestPredicates.accept(MediaType.ALL)), swaggerSecurityHandler);
    }

}

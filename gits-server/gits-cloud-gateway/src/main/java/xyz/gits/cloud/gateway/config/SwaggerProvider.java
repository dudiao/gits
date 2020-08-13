package xyz.gits.cloud.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 网关swagger聚合
 *
 * @author songyinyin
 * @date 2020/5/1 上午 12:35
 */
@Primary
@Component
public class SwaggerProvider implements SwaggerResourcesProvider {

    private static final String API_URI = "/v2/api-docs";

    @Autowired
    private DiscoveryClient discoveryClient;
    @Value("${spring.application.name:gits-gateway}")
    private String applicationName;

    @Override
    public List<SwaggerResource> get() {
        List<String> services = discoveryClient.getServices();
        return services.stream()
            .filter(serviceName -> !applicationName.equals(serviceName))
            .map(serviceName -> swaggerResource(serviceName, "/" + serviceName + API_URI))
            .collect(Collectors.toList());
    }

    private static SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}

package xyz.gits.cloud.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author songyinyin
 * @date 2020/5/1 上午 12:35
 */
@Primary
@Component
public class SwaggerProvider implements SwaggerResourcesProvider {

    private static final String API_URI = "/v2/api-docs";

    @Autowired
    private RouteDefinitionRepository routeDefinitionRepository;

    @Override
    public List<SwaggerResource> get() {
        List<RouteDefinition> routes = new ArrayList<>();
        routeDefinitionRepository.getRouteDefinitions().subscribe(routes::add);
        return routes.stream().flatMap(routeDefinition -> routeDefinition.getPredicates().stream()
                .filter(predicateDefinition -> "Path".equalsIgnoreCase(predicateDefinition.getName()))
                .map(predicateDefinition ->
                        swaggerResource(routeDefinition.getId(), predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0").replace("/**", API_URI))
                )).sorted(Comparator.comparing(SwaggerResource::getName))
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

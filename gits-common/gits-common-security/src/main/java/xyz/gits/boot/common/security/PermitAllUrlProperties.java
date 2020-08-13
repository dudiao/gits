package xyz.gits.boot.common.security;

import cn.hutool.core.util.ReUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import xyz.gits.boot.common.security.annotation.Inner;

import java.util.*;
import java.util.regex.Pattern;


/**
 * 对外直接暴露URL
 *
 * @author songyinyin
 * @date 2020/8/6 上午 12:02
 */
@Slf4j
@ConfigurationProperties(prefix = PermitAllUrlProperties.PREFIX)
public class PermitAllUrlProperties implements InitializingBean {

    @Autowired
    private WebApplicationContext applicationContext;

    /**
     * Prefix of {@link PermitAllUrlProperties}.
     */
    public static final String PREFIX = "gits.security";

    private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");

    @Getter
    @Setter
    private List<String> ignoreUrls = new ArrayList<>();

    @Override
    public void afterPropertiesSet() {
        // 放行 @Inner 注解的URI
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        map.keySet().forEach(info -> {
            HandlerMethod handlerMethod = map.get(info);

            // 获取方法上边的注解 替代path variable 为 *
            Inner method = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), Inner.class);
            Optional.ofNullable(method).ifPresent(inner -> info.getPatternsCondition().getPatterns()
                .forEach(url -> ignoreUrls.add(ReUtil.replaceAll(url, PATTERN, "*"))));

            // 获取类上边的注解, 替代path variable 为 *
            Inner controller = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), Inner.class);
            Optional.ofNullable(controller).ifPresent(inner -> info.getPatternsCondition().getPatterns()
                .forEach(url -> ignoreUrls.add(ReUtil.replaceAll(url, PATTERN, "*"))));
        });

        // 放行 swagger 相关路径
        String[] AUTH_WHITELIST = {
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger-resources/**",
            "/v2/**",
            "/csrf",
            "/",

            // other
            "/open/**",
            "/actuator/**",
            "/favicon.ico",
        };
        ignoreUrls.addAll(Arrays.asList(AUTH_WHITELIST));
    }
}

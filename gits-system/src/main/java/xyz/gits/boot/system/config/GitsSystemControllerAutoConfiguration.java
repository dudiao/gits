package xyz.gits.boot.system.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.gits.boot.system.controller.*;

/**
 * @author songyinyin
 * @date 2020/4/27 下午 03:02
 */
@Configuration
public class GitsSystemControllerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ConcurrentSessionController concurrentSessionController() {
        return new ConcurrentSessionController();
    }

    @Bean
    @ConditionalOnMissingBean
    public OrgController orgController() {
        return new OrgController();
    }

    @Bean
    @ConditionalOnMissingBean
    public ResourceController resourceController() {
        return new ResourceController();
    }

    @Bean
    @ConditionalOnMissingBean
    public RoleController roleController() {
        return new RoleController();
    }

    @Bean
    @ConditionalOnMissingBean
    public UserController userController() {
        return new UserController();
    }

}

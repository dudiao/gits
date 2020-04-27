package xyz.gits.boot.system.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.gits.boot.api.system.service.SystemService;
import xyz.gits.boot.system.service.*;
import xyz.gits.boot.system.service.impl.*;

/**
 * @author songyinyin
 * @date 2020/4/27 下午 03:02
 */
@Configuration
public class GitsSystemServiceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public IOrgService orgService() {
        return new OrgServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public IResourceService resourceService() {
        return new ResourceServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public IRoleResourceRelService roleResourceRelService() {
        return new RoleResourceRelServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public IRoleService roleService() {
        return new RoleServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public IUserRoleRelService userRoleRelService() {
        return new UserRoleRelServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public IUserService userService() {
        return new UserServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public SystemService systemService() {
        return new SystemServiceImpl();
    }

}

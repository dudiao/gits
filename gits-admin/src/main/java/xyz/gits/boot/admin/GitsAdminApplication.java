package xyz.gits.boot.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * SpringBoot监控
 * <p>
 *     k8s环境中，需要添加注解 @EnableDiscoveryClient，同时引入依赖：spring-cloud-starter-kubernetes-all
 * </p>
 * <p>
 *     添加 @EnableScheduling 会定时刷新微服务的实例
 * </p>
 *
 * @author songyinyin
 */
@EnableScheduling
@EnableAdminServer
@EnableDiscoveryClient
@SpringBootApplication
public class GitsAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(GitsAdminApplication.class, args);
    }

}

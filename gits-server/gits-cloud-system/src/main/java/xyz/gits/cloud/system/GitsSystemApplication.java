package xyz.gits.cloud.system;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import xyz.gits.boot.common.security.annotation.EnableGitsClientServer;

@EnableFeignClients
@EnableGitsClientServer
@SpringCloudApplication
@EnableCreateCacheAnnotation
@EnableMethodCache(basePackages = {"xyz.gits.boot.system", "xyz.gits.cloud.system"})
public class GitsSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(GitsSystemApplication.class, args);
    }

}

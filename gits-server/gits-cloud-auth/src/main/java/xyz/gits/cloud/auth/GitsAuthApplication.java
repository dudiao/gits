package xyz.gits.cloud.auth;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableSwagger2Doc
@EnableMethodCache(basePackages = "xyz.gits.boot")
@EnableCreateCacheAnnotation
@EnableFeignClients(basePackages = "xyz.gits.boot.api.remote")
@SpringBootApplication
public class GitsAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(GitsAuthApplication.class, args);
    }

}

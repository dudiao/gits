package xyz.gits.boot;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSwagger2Doc
@EnableMethodCache(basePackages = "xyz.gits.boot")
@EnableCreateCacheAnnotation
@SpringBootApplication
public class GitsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GitsApplication.class, args);
    }

}

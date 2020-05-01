package xyz.gits.cloud.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "xyz.gits.boot.api.remote")
@SpringBootApplication
public class GitsAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(GitsAuthApplication.class, args);
    }

}

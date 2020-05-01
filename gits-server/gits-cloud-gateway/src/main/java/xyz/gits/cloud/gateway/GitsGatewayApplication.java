package xyz.gits.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * Gits 网关
 */
@SpringCloudApplication
public class GitsGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GitsGatewayApplication.class, args);
    }

}

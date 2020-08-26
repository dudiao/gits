package xyz.gits.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import xyz.gits.cloud.gateway.loadbalance.LoadBalancerConfiguration;

/**
 * Gits 网关
 */
@SpringCloudApplication
@LoadBalancerClients(defaultConfiguration = {LoadBalancerConfiguration.class})
public class GitsGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GitsGatewayApplication.class, args);
    }

}

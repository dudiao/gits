package xyz.gits.boot.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAdminServer
@SpringBootApplication
public class GitsAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(GitsAdminApplication.class, args);
    }

}

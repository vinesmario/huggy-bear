package com.vinesmario.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class CloudRegistryServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudRegistryServerApplication.class, args);
    }

}

package com.vinesmario.microservice.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringCloudApplication
@ComponentScan("com.vinesmario.microservice")
@MapperScan("com.vinesmario.microservice.server")
@EnableFeignClients("com.vinesmario.microservice")
public class UaaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UaaServerApplication.class, args);
    }

}

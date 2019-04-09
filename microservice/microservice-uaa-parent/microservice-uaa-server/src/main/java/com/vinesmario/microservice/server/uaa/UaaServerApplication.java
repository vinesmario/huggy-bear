package com.vinesmario.microservice.server.uaa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringCloudApplication
@EnableFeignClients
@ComponentScan("com.vinesmario.microservice")
@MapperScan("com.vinesmario.microservice.server")
public class UaaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UaaServerApplication.class, args);
    }

}

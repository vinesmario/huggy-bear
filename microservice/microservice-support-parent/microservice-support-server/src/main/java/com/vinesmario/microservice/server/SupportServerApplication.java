package com.vinesmario.microservice.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@SpringCloudApplication
@ComponentScan("com.vinesmario.microservice")
@MapperScan("com.vinesmario.microservice.server")
@EnableOAuth2Client
@EnableScheduling
public class SupportServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SupportServerApplication.class, args);
    }

}



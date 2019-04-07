package com.vinesmario.microservice.server.uaa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.vinesmario.microservice.server")
@ComponentScan("com.vinesmario.microservice")
public class ServerApplicationIntTest {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplicationIntTest.class, args);
    }

}

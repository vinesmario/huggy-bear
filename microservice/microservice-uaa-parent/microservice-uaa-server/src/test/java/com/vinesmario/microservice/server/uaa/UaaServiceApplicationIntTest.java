package com.vinesmario.microservice.server.uaa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.vinesmario.microservice.server")
@ComponentScan("com.vinesmario.microservice")
public class UaaServiceApplicationIntTest {

    public static void main(String[] args) {
        SpringApplication.run(UaaServiceApplicationIntTest.class, args);
    }

}

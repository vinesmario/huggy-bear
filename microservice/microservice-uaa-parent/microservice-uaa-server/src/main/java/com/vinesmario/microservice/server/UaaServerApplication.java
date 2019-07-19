package com.vinesmario.microservice.server;

import com.vinesmario.microservice.client.common.web.feign.interceptor.OAuth2UserFeignClientConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringCloudApplication
//@ComponentScan("com.vinesmario.microservice")
@ComponentScan(
        basePackages = {"com.vinesmario.microservice"},
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {OAuth2UserFeignClientConfiguration.class})
)
@MapperScan("com.vinesmario.microservice.server")
public class UaaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UaaServerApplication.class, args);
    }

}

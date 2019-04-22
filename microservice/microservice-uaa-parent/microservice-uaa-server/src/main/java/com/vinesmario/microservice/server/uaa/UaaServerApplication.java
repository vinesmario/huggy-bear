package com.vinesmario.microservice.server.uaa;

import com.vinesmario.microservice.client.common.web.feign.interceptor.OAuth2ServiceFeignClientConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.oauth2.config.annotation.web.configuration.OAuth2ClientConfiguration;

@SpringCloudApplication
@ComponentScan(
        basePackages = {"com.vinesmario.microservice"},
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {OAuth2ClientConfiguration.class, OAuth2ServiceFeignClientConfiguration.class})
)
@MapperScan("com.vinesmario.microservice.server")
@EnableFeignClients("com.vinesmario.microservice")
public class UaaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UaaServerApplication.class, args);
    }

}

package com.vinesmario.microservice.server;

import com.vinesmario.microservice.client.common.web.feign.interceptor.OAuth2FeignClientConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * 鉴权服务器应当不依赖与其他服务，不需要启用feign
 */
@SpringCloudApplication
@ComponentScan(
        basePackages = {"com.vinesmario.microservice"},
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {OAuth2FeignClientConfiguration.class})
)
@MapperScan("com.vinesmario.microservice.server")
public class UaaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UaaServerApplication.class, args);
    }

}

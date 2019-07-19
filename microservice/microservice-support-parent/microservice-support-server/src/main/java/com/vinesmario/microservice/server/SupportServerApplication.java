package com.vinesmario.microservice.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.OAuth2ClientConfiguration;

/**
 * See org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2RestOperationsConfiguration.class
 * <p>
 * ConditionalOnMissingBean(OAuth2ClientConfiguration.class)
 * protected static class RequestScopedConfiguration {...}
 * 使用@EnableOAuth2Client的同时，
 * 想要使得OAuth2FeignRequestInterceptor拦截器作用域于为request，
 * 就不得装载OAuth2ClientConfiguration.class
 */
@SpringCloudApplication
@ComponentScan(
        basePackages = ("com.vinesmario.microservice"),
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {OAuth2ClientConfiguration.class})
)
@MapperScan("com.vinesmario.microservice.server")
@EnableFeignClients("com.vinesmario.microservice")
@EnableOAuth2Client
//@EnableScheduling
public class SupportServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SupportServerApplication.class, args);
    }

}



package com.vinesmario.microservice.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringCloudApplication
@ComponentScan("com.vinesmario.microservice")
//@ComponentScan(
//        basePackages = {"com.vinesmario.microservice"},
//        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
//                classes = {OAuth2ClientConfiguration.class, OAuth2ServiceFeignClientConfiguration.class})
//)
@MapperScan("com.vinesmario.microservice.server")
@EnableFeignClients("com.vinesmario.microservice")
@EnableOAuth2Client
public class SupportServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SupportServerApplication.class, args);
    }

    /**
     * @EnableOAuth2Client Error creating bean with name 'scopedTarget.oauth2ClientContext':
     * Scope 'session' is not active for the current thread;
     * consider defining a scoped proxy for this bean
     * if you intend to refer to it from a singleton
     */
    @Primary
    @Bean
    public OAuth2ClientContext singletonClientContext() {
        return new DefaultOAuth2ClientContext();
    }

}



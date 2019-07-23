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
 * 想要透传access_token，OAuth2FeignRequestInterceptor拦截器的作用域必须为request，
 * 可见源码：
 * org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2RestOperationsConfiguration
 * ConditionalOnMissingBean(OAuth2ClientConfiguration.class)
 * Conditional({ OAuth2ClientIdCondition.class, NoClientCredentialsCondition.class })
 * protected static class RequestScopedConfiguration {...}
 * 条件1：使用@EnableOAuth2Client
 * 条件2：不能装载OAuth2ClientConfiguration.class
 * 条件3：security.oauth2.client.grant-type属性的为空，或者值不包含client_credentials
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
public class SupportServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SupportServerApplication.class, args);
    }

}



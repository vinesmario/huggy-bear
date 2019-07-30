package com.vinesmario.microservice.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.OAuth2ClientConfiguration;

/**
 * 想要以无用户（client_credentials）方式获取access_token并传递，该拦截器作用域必须为singleton，
 * 可见源码：
 * org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2RestOperationsConfiguration
 * Conditional(ClientCredentialsCondition.class)
 * protected static class SingletonScopedConfiguration {...}
 * 条件1：使用@EnableOAuth2Client
 * 条件2：这条是不存在的
 * 条件3：security.oauth2.client.grant-type属性值包含client_credentials
 * oauth2ClientContext中的access_token应及时去获取或刷新，
 * 特性：在controller中接受并处理request请求的方法中，以feign的方式调用外部接口，并不会透传access_token，
 * 而是传递无用户（client_credentials）方式获取的access_token
 */
@SpringCloudApplication
@ComponentScan("com.vinesmario.microservice")
//@ComponentScan(
//        basePackages = ("com.vinesmario.microservice"),
//        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
//                classes = {OAuth2ClientConfiguration.class})
//)
@MapperScan("com.vinesmario.microservice.server")
@EnableFeignClients("com.vinesmario.microservice")
@EnableOAuth2Client
@EnableScheduling
public class ScheduleServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleServerApplication.class, args);
    }

}



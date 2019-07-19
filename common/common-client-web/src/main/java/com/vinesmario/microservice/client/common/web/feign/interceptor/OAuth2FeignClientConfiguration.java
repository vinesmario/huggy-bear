package com.vinesmario.microservice.client.common.web.feign.interceptor;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

/**
 * 透传access_token方法一
 * 比方法二更通用
 * 因为同时还可装填资源服务器以client_credentials方式获取的access_token
 */
@Slf4j
@Configuration
public class OAuth2FeignClientConfiguration {

    @Autowired
    private OAuth2ClientContext oauth2ClientContext;
    @Autowired
    private OAuth2ProtectedResourceDetails details;

    @Bean(name = "oAuth2FeignClientInterceptor")
    public RequestInterceptor getOAuth2FeignClientInterceptor() {
        // 想要透传access_token，该拦截器作用域必须为request，
        // 使用@EnableOAuth2Client装载自动配置时，不能装载OAuth2ClientConfiguration.class
        // 可见源码：
        // org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2RestOperationsConfiguration
        // @ConditionalOnMissingBean(OAuth2ClientConfiguration.class)
        // protected static class RequestScopedConfiguration {...}
        // 踩坑①：拦截器作用域为request，意味着程序初始化时oauth2ClientContext内容为空。
        // 在此处调用oauth2ClientContext的方法，例如日志打印oauth2ClientContext.getAccessToken()，
        // 则程序会报错，并提示将该拦截器更改为session作用域，其实该提示与透传access_token的目的是背道而驰的。
        // 踩坑②：如果使用了hystrix，则hystrix业务处理与controller接收并处理request请求的业务不在同一个线程内，
        // 同时，此也会导致此处SecurityContextHolder.getContext()，
        // RequestContextHolder.getRequestAttributes()等容器获得的值为空。
        // 踩坑明细可见 https://github.com/spring-cloud/spring-cloud-netflix/issues/1330
        return new OAuth2FeignRequestInterceptor(oauth2ClientContext, details);
    }

}

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
 * 由用户或无用户（其他资源服务器、客户端定时任务）发起请求时，透传access_token
 * 资源服务器定时任务发起请求时，传递access_token
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
        // 以自动配置方式使用oauth2Client
        // 想要透传access_token，该拦截器作用域必须为request，
        // 可见源码：
        // org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2RestOperationsConfiguration
        // @ConditionalOnMissingBean(OAuth2ClientConfiguration.class)
        // @Conditional({ OAuth2ClientIdCondition.class, NoClientCredentialsCondition.class })
        // protected static class RequestScopedConfiguration {...}
        // 条件1：使用@EnableOAuth2Client
        // 条件2：不能装载OAuth2ClientConfiguration.class
        // 条件3：security.oauth2.client.grant-type属性的为空，或者值不包含client_credentials
        // 踩坑①：拦截器作用域为request，意味着程序初始化时oauth2ClientContext内容为空。
        // 若在此处调用oauth2ClientContext的方法，例如日志中打印oauth2ClientContext.getAccessToken()的值，
        // 则程序会报错，并提示将该拦截器更改为session作用域，其实该提示与透传access_token的目的是背道而驰的。
        // 踩坑②：如果使用了hystrix，则hystrix业务处理与controller接收并处理request请求的业务不在同一个线程内，
        // 同时，此也会导致此处SecurityContextHolder.getContext()，
        // RequestContextHolder.getRequestAttributes()等容器获得的值为空。
        // 具体明细可见 https://github.com/spring-cloud/spring-cloud-netflix/issues/1330

        // 想要以无用户（client_credentials）方式获取access_token并传递，该拦截器作用域必须为session，
        // 可见源码：
        // org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2RestOperationsConfiguration
        // @ConditionalOnBean(OAuth2ClientConfiguration.class)
        // @Conditional({ OAuth2ClientIdCondition.class, NoClientCredentialsCondition.class })
        // protected static class SessionScopedConfiguration {...}
        // 条件1：使用@EnableOAuth2Client
        // 条件2：需要装载OAuth2ClientConfiguration.class
        // 条件3：security.oauth2.client.grant-type属性的为空，或者值不包含client_credentials
        return new OAuth2FeignRequestInterceptor(oauth2ClientContext, details);
    }

}

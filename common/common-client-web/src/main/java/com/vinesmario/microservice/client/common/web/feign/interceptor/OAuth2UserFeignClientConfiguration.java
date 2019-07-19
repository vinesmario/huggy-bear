package com.vinesmario.microservice.client.common.web.feign.interceptor;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;


@Slf4j
@Configuration
public class OAuth2UserFeignClientConfiguration {

    @Autowired
    private OAuth2ClientContext oauth2ClientContext;
    @Autowired
    private OAuth2ProtectedResourceDetails details;

    @Bean(name = "userFeignClientInterceptor")
    public RequestInterceptor getUserFeignClientInterceptor() {
        // 使用@EnableOAuth2Client注解，会启用autoconfig自动配置项，
        // 此时oauth2ClientContext作用域由资源服务器的配置项security.oauth2.client.grantType决定。
        // 以是否包含client_credentials为区别。
        // oauth2ClientContext中的token可以是前端请求的access_token，可用以透传access_token
        // 也可以是资源服务器端以client_credentials方式获取access_token
        // 如果使用了hystrix，hystrix是另起线程，与处理request请求业务不在同一个线程，则securityContext为空。
        // 需要设置相关参数
        // See https://github.com/spring-cloud/spring-cloud-netflix/issues/1330
        log.info("AccessToken: " + oauth2ClientContext.getAccessToken().getValue());
        return new OAuth2FeignRequestInterceptor(oauth2ClientContext, details);
        // 方式②只适合于透传access_token
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        log.info("client-SecurityContext: " + securityContext.toString());
//        Authentication authentication = securityContext.getAuthentication();
//        log.info("client-authentication: " + authentication);
//        if (authentication != null && authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
//            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
//            log.info("AccessToken: " + details.getTokenValue());
//            return (template) -> template.header(OAuth2FeignRequestInterceptor.AUTHORIZATION,
//                    String.format("%s %s", OAuth2FeignRequestInterceptor.BEARER, details.getTokenValue()));
//        }
//        return null;
    }
}

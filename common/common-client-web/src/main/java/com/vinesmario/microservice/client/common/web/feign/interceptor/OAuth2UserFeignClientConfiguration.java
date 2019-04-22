package com.vinesmario.microservice.client.common.web.feign.interceptor;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class OAuth2UserFeignClientConfiguration {

    @Autowired
    private OAuth2ClientContext oauth2ClientContext;
    @Autowired
    private OAuth2ProtectedResourceDetails details;

    @Bean(name = "userFeignClientInterceptor")
    public RequestInterceptor getUserFeignClientInterceptor() throws IOException {
        // 透传 accesstoken
        return new OAuth2FeignRequestInterceptor(oauth2ClientContext, details);
    }
}

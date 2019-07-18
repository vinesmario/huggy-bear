//package com.vinesmario.microservice.client.common.web.feign.interceptor;
//
//import feign.RequestInterceptor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.oauth2.client.OAuth2ClientContext;
//import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
//import org.springframework.web.client.RestTemplate;
//
//import java.io.IOException;
//
//@Slf4j
//public class OAuth2ServiceFeignClientConfiguration {
//
//    @Autowired
//    private RestTemplate restTemplate;
//    @Autowired
//    private OAuth2ClientContext oauth2ClientContext;
//    @Autowired
//    private OAuth2ProtectedResourceDetails details;
//
//    @Bean(name = "serviceFeignClientInterceptor")
//    public RequestInterceptor getServiceFeignClientInterceptor() throws IOException {
//        // 获取客户端角色的 accesstoken
//        return new OAuth2ServiceFeignClientInterceptor();
//    }
//
//}

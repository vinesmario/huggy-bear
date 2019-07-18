//package com.vinesmario.microservice.client.common.web.feign.interceptor;
//
//import feign.RequestInterceptor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//
//import java.io.IOException;
//
//@Slf4j
//public class OAuth2UserFeignClientConfiguration {
//
//    @Bean(name = "userFeignClientInterceptor")
//    public RequestInterceptor getUserFeignClientInterceptor() throws IOException {
//        // 透传 accesstoken
//        return new OAuth2UserFeignClientInterceptor();
//    }
//
//}

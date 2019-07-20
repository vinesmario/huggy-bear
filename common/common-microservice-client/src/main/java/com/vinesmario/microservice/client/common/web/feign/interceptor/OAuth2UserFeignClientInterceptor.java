//package com.vinesmario.microservice.client.common.web.feign.interceptor;
//
//import feign.RequestInterceptor;
//import feign.RequestTemplate;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
//
///**
// * 透传access_token方法二
// */
//@Slf4j
//@Configuration
//public class OAuth2UserFeignClientInterceptor implements RequestInterceptor {
//
//    private static final String AUTHORIZATION = OAuth2FeignRequestInterceptor.AUTHORIZATION;
//    private static final String BEARER = OAuth2FeignRequestInterceptor.BEARER;
//
//    @Override
//    public void apply(RequestTemplate template) {
//        // 踩坑：如果使用了hystrix，则hystrix业务处理与controller接收并处理request请求的业务不在同一个线程内，
//        // 同时，此也会导致此处SecurityContextHolder.getContext()，
//        // RequestContextHolder.getRequestAttributes()等容器获得的值为空。
//        // 踩坑明细可见 https://github.com/spring-cloud/spring-cloud-netflix/issues/1330
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        Authentication authentication = securityContext.getAuthentication();
//        if (authentication != null && authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
//            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
//            log.info("AccessToken: " + details.getTokenValue());
//            template.header(AUTHORIZATION, String.format("%s %s", BEARER, details.getTokenValue()));
//        }
//    }
//
//}

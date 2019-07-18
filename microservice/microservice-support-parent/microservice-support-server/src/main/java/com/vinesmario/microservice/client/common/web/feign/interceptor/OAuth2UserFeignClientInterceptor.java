//package com.vinesmario.microservice.client.common.web.feign.interceptor;
//
//import feign.RequestInterceptor;
//import feign.RequestTemplate;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//public class OAuth2UserFeignClientInterceptor implements RequestInterceptor {
//
//    private static final String AUTHORIZATION = OAuth2FeignRequestInterceptor.AUTHORIZATION;
//    private static final String BEARER = OAuth2FeignRequestInterceptor.BEARER;
//
//    @Override
//    public void apply(RequestTemplate template) {
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        Authentication authentication = securityContext.getAuthentication();
//        log.info("client-authentication: " + authentication);
//        if (authentication != null && authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
//            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
//            template.header(AUTHORIZATION, String.format("%s %s", BEARER, details.getTokenValue()));
//        }
//    }
//
//}

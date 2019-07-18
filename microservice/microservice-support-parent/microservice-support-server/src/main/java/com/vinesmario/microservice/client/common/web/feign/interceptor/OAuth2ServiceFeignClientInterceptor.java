package com.vinesmario.microservice.client.common.web.feign.interceptor;//package com.vinesmario.microservice.client.common.web.feign.interceptor;
//
//import com.vinesmario.microservice.client.common.security.ServiceTokenEndpointClient;
//import feign.RequestInterceptor;
//import feign.RequestTemplate;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//public class OAuth2ServiceFeignClientInterceptor implements RequestInterceptor {
//
//    private static final String AUTHORIZATION = OAuth2FeignRequestInterceptor.AUTHORIZATION;
//    private static final String BEARER = OAuth2FeignRequestInterceptor.BEARER;
//
//    @Autowired
//    private ServiceTokenEndpointClient serviceTokenEndpointClient;
//
//    @Override
//    public void apply(RequestTemplate template) {
//        OAuth2AccessToken oauthToken = serviceTokenEndpointClient.sendClentCredentialsGrant();
//        if (oauthToken != null) {
//            template.header(AUTHORIZATION, String.format("%s %s", BEARER, oauthToken.getValue()));
//        }
//    }
//
//}

package com.vinesmario.microservice.client.common.web.feign.interceptor;

import com.vinesmario.microservice.client.common.security.ServiceTokenEndpointClient;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Slf4j
@Component
public class OAuth2ServiceFeignClientConfiguration {

    //    private final LoadBalancedResourceDetails loadBalancedResourceDetails;
//
//    public OAuth2InterceptedFeignConfiguration(LoadBalancedResourceDetails loadBalancedResourceDetails) {
//        this.loadBalancedResourceDetails = loadBalancedResourceDetails;
//    }
//
//    @Bean(name = "oauth2RequestInterceptor")
//    public RequestInterceptor getOAuth2RequestInterceptor() throws IOException {
//        return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), loadBalancedResourceDetails);
//    }

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private OAuth2ProtectedResourceDetails oAuth2ProtectedDetails;
    @Autowired
    private OAuth2ClientContext oauth2ClientContext;

    @Bean(name = "serviceFeignClientInterceptor")
    public RequestInterceptor getServiceFeignClientInterceptor() throws IOException {
        // 获取客户端角色的 accesstoken
        ServiceTokenEndpointClient serviceTokenEndpointClient = new ServiceTokenEndpointClient(restTemplate, oAuth2ProtectedDetails);
        OAuth2AccessToken accessToken = serviceTokenEndpointClient.sendClentCredentialsGrant();
        oauth2ClientContext.setAccessToken(accessToken);
        return new OAuth2FeignRequestInterceptor(oauth2ClientContext, oAuth2ProtectedDetails);
    }


}

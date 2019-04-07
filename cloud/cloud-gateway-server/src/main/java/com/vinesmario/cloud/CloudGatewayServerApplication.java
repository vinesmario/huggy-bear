package com.vinesmario.cloud;

import com.vinesmario.cloud.filter.AccessFilter;
import com.vinesmario.cloud.filter.CustomErrorFilter;
import com.vinesmario.cloud.filter.ResponseBodyFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@SpringCloudApplication
public class CloudGatewayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudGatewayServerApplication.class, args);
    }

    //@Bean
    public AccessFilter accessFilter() {
        return new AccessFilter();
    }

    @Bean
    public ResponseBodyFilter responseBodyFilter() {
        return new ResponseBodyFilter();
    }

    @Bean
    public CustomErrorFilter customErrorFilter() {
        return new CustomErrorFilter();
    }

}

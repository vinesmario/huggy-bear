package com.vinesmario.cloud;

import com.vinesmario.cloud.filter.AccessFilter;
import com.vinesmario.cloud.filter.CustomErrorFilter;
import com.vinesmario.cloud.filter.ResponseBodyFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * @RefreshScope 当application.yml配置文件发生变化的时候，
 * 不需要手动的进行重启，调用post请求http://host:port/refresh,就会加载新的配置文件，
 * 正在访问的客户并不影响还是使用旧的配置文件，后来的用户会使用新的配置文件。
 */
@EnableZuulProxy
@SpringCloudApplication
@RefreshScope
public class CloudGatewayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudGatewayServerApplication.class, args);
    }

    @Bean
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

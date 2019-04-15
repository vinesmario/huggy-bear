package com.vinesmario.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.turbine.stream.EnableTurbineStream;

/**
 * @RefreshScope 当application.yml配置文件发生变化的时候，
 * 不需要手动的进行重启，调用post请求http://host:port/refresh,就会加载新的配置文件，
 * 正在访问的客户并不影响还是使用旧的配置文件，后来的用户会使用新的配置文件。
 */

@SpringBootApplication
@EnableTurbineStream
public class CloudTurbineStreamApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudTurbineStreamApplication.class, args);
    }


}

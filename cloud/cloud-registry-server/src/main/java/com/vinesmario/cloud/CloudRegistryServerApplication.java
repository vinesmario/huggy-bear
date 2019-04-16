package com.vinesmario.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@SpringBootApplication
@EnableEurekaServer
public class CloudRegistryServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudRegistryServerApplication.class, args);
    }

    @Configuration
    public static class SecuritySecureConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
            // 关闭csrf
            http.csrf().disable();
            // 为了可以使用 http://${user}:${password}@${hostname}:${port}/eureka/ 这种方式
            // 所以必须是HttpBasic。
            http.authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                    .httpBasic();
        }
    }

}

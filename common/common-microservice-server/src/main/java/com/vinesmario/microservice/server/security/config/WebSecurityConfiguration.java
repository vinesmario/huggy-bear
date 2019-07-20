package com.vinesmario.microservice.server.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * As is typical of a Spring Security web application,
 * users are defined in a WebSecurityConfigurerAdapter instance.
 *
 * <p>
 * 三种方法级权限控制
 * <p>
 * 1.prePostEnabled: expression-based
 * 2.securedEnabled: Spring Security’s native annotation
 * 3.jsr250Enabled: standards-based and allow simple role-based constraints
 * <p>
 * WebSecurityConfigurerAdapter用于保护oauth相关的endpoints，
 * 同时主要作用于用户的登录(form login,Basic auth)
 * 在WebSecurityConfigurerAdapter不拦截oauth要开放的资源
 */
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers("/app/**/*.{js,html}")
                .antMatchers("/bower_components/**")
                .antMatchers("/i18n/**")
                .antMatchers("/content/**")
                .antMatchers("/swagger-ui/index.html")
                .antMatchers("/test/**")
                .antMatchers("/h2-console/**");
    }



}

package com.vinesmario.microservice.server.security.config;

import com.vinesmario.microservice.server.security.model.AuthoritiesConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletResponse;

/**
 * ResourceServerConfigurerAdapter用于保护oauth要开放的资源，
 * 同时主要作用于client端以及token的认证(Bearer auth)
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    private ResourceServerProperties resourceServerProperties;
    @Autowired
    private CorsFilter corsFilter;

    /**
     * Apply the token converter (and enhancer) for token store.
     *
     * @return the JwtTokenStore managing the tokens.
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * This bean generates an token enhancer, which manages the exchange between JWT acces tokens and Authentication
     * in both directions.
     *
     * @return an access token converter configured with the authorization server's public/private keys
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey(resourceServerProperties.getJwt().getKeyValue());
        return accessTokenConverter;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // 如果是在authorization server，
        // 配置文件中的security.oauth2.resource.id参数不起作用
        // 源码中默认resourceId为oauth2-resource，见ResourceServerSecurityConfigurer.class
        // 需重写原方法
        // 其他资源服务器配置可生效，不需重写。
        resources.resourceId(resourceServerProperties.getResourceId()).tokenStore(tokenStore());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .csrf().disable()
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .headers().frameOptions().disable()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
//                .antMatchers("/api/register").permitAll()
//                .antMatchers("/api/activate").permitAll()
//                .antMatchers("/api/authenticate").permitAll()
//                .antMatchers("/api/account/reset-password/init").permitAll()
//                .antMatchers("/api/account/reset-password/finish").permitAll()
//                .antMatchers("/api/profile-info").permitAll()
                .antMatchers("/api/**").authenticated()
                .antMatchers("/management/health").permitAll()
                .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
                .antMatchers("/v2/api-docs/**").permitAll()
                .antMatchers("/swagger-resources/configuration/ui").permitAll()
                .antMatchers("/swagger-ui/index.html").hasAuthority(AuthoritiesConstants.ADMIN);
    }

}

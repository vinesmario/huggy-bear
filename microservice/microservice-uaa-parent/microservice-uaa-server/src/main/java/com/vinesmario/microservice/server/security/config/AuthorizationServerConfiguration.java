package com.vinesmario.microservice.server.security.config;

import com.vinesmario.microservice.server.security.model.ClientDetailsServiceImpl;
import com.vinesmario.microservice.server.security.model.UserDetailsServiceImpl;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.OAuth2AuthorizationServerConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 该配置问价文件中不宜使用@Bean注解创建TokenStore对象，
 * 否则回出现循环调用。
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends OAuth2AuthorizationServerConfiguration {

    @Autowired
    private ResourceServerProperties resourceServerProperties;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private ClientDetailsServiceImpl clientDetailsService;
    @Autowired
    private ApplicationContext applicationContext;

    public AuthorizationServerConfiguration(BaseClientDetails details,
                                            AuthenticationConfiguration authenticationConfiguration,
                                            ObjectProvider<TokenStore> tokenStore,
                                            ObjectProvider<AccessTokenConverter> tokenConverter,
                                            AuthorizationServerProperties properties) throws Exception {
        super(details, authenticationConfiguration, tokenStore, tokenConverter, properties);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        /**
         *  find config from properties 'security.oauth2.authorization...'
         */
        super.configure(oauthServer);
//        oauthServer.tokenKeyAccess("permitAll()")
//                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /**
         *  find details from properties 'security.oauth2.client...'
         */
//        super.configure(clients);
        /**
         * For a better client design, this should be done by a ClientDetailsService (similar to UserDetailsService, find details from database).
         */
        clients.withClientDetails(clientDetailsService);
    }

    /**
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        super.configure(endpoints);
        //pick up all TokenEnhancers incl. those defined in the application
        //this avoids changes to this class if an application wants to add its own to the chain
        Collection<TokenEnhancer> tokenEnhancers = applicationContext.getBeansOfType(TokenEnhancer.class).values();
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(new ArrayList<>(tokenEnhancers));
        // 默认使用org.springframework.security.oauth2.provider.token.DefaultTokenServices.class处理token
        endpoints.userDetailsService(userDetailsService)
                .tokenEnhancer(tokenEnhancerChain)
                .reuseRefreshTokens(false);//don't reuse or we will run into session inactivity timeouts
    }

}

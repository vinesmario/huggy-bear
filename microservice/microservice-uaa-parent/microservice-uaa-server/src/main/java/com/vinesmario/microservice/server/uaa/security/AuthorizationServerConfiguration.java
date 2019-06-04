package com.vinesmario.microservice.server.uaa.security;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.OAuth2AuthorizationServerConfiguration;
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
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends OAuth2AuthorizationServerConfiguration {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private ClientDetailsServiceImpl clientDetailsService;
    @Autowired
    private ApplicationContext applicationContext;
    // ResourceServerConfiguration中创建Bean
    @Autowired
    private TokenStore tokenStore;

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
         *  find config from properties
         */
        super.configure(oauthServer);
//        oauthServer.tokenKeyAccess("permitAll()")
//                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /**
         *  find details from properties
         */
        super.configure(clients);
        /*
        For a better client design, this should be done by a ClientDetailsService (similar to UserDetailsService, find details from database).
         */
//        clients.withClientDetails(clientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        super.configure(endpoints);
        //pick up all TokenEnhancers incl. those defined in the application
        //this avoids changes to this class if an application wants to add its own to the chain
        Collection<TokenEnhancer> tokenEnhancers = applicationContext.getBeansOfType(TokenEnhancer.class).values();
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(new ArrayList<>(tokenEnhancers));
        endpoints.userDetailsService(userDetailsService)
                .tokenStore(tokenStore)
                .tokenEnhancer(tokenEnhancerChain)
                .reuseRefreshTokens(false);//don't reuse or we will run into session inactivity timeouts
    }

}

package com.vinesmario.microservice.server.uaa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class TokenStoreConfig {

    @Autowired
    private Oauth2Properties oauth2Properties;

    /**
     * Apply the token converter (and enhancer) for token store.
     *
     * @return the JwtTokenStore managing the tokens.
     */
    @Bean
    public JwtTokenStore tokenStore() {
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
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        // 非对称加密
//        KeyPair keyPair = new KeyStoreKeyFactory(
//                new ClassPathResource(oauth2Properties.getJwt().getKeyStore().getName()), oauth2Properties.getJwt().getKeyStore().getPassword().toCharArray())
//                .getKeyPair(oauth2Properties.getJwt().getKeyStore().getAlias());
//        converter.setKeyPair(keyPair);
        // 对称加密
        converter.setSigningKey(oauth2Properties.getJwt().getSigningKey());
        return converter;
    }

}

package com.vinesmario.microservice.server.uaa.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Data
@Configuration
@ConfigurationProperties("oauth2")
public class Oauth2Properties {

    private Map<String, Oauth2Properties.SampleClientDetail> clientDetailsStore = new LinkedHashMap();
    private Oauth2Properties.JWT jwt;

    @Data
    public static class SampleClientDetail implements ClientDetails {

        @Autowired
        private PasswordEncoder passwordEncoder;

        private String clientId;
        private boolean secretRequired = true;
        private String clientSecret;
        private boolean scoped = true;
        private Set<String> scope;
        private Set<String> resourceIds;
        private Set<String> authorizedGrantTypes;
        private Set<GrantedAuthority> authorities;
        private Set<String> registeredRedirectUri;
        private Integer accessTokenValiditySeconds;
        private Integer refreshTokenValiditySeconds;
        private boolean autoApprove = true;
        private Map<String, Object> additionalInformation;

        // 加密
        @Override
        public String getClientSecret() {
            return passwordEncoder.encode(this.clientSecret);
        }

        @Override
        public boolean isAutoApprove(String s) {
            return false;
        }
    }

    @Data
    public static class JWT {

        private JWT.KeyStore keyStore = new JWT.KeyStore();
        private String signingKey;

        @Data
        public static class KeyStore {
            //name of the keystore in the classpath
            private String name = "keystore.jks";
            //password used to access the key
            private String password = "password";
            //name of the alias to fetch
            private String alias = "selfsigned";

        }
    }

}

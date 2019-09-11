package com.vinesmario.microservice.server.security.model;

import com.vinesmario.microservice.server.uaa.service.UserAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

/**
 * Adds the standard "iat" claim to tokens so we know when they have been created.
 * This is needed for a session timeout due to inactivity (ignored in case of "remember-me").
 */
@Slf4j
@Component
public class IatTokenEnhancer implements TokenEnhancer {

    @Autowired
    private UserAccountService userAccountService;
//    @Autowired
//    private ClientDetailsServiceImpl clientDetailsService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        addClaims((DefaultOAuth2AccessToken) accessToken, authentication);
        return accessToken;
    }

    private void addClaims(DefaultOAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        if (accessToken.getAdditionalInformation().isEmpty()) {
            accessToken.setAdditionalInformation(new LinkedHashMap<>());
        }
        //add "iat" claim with current time in secs
        //this is used for an inactive session timeout
        accessToken.getAdditionalInformation().put("iat", new Integer((int) (System.currentTimeMillis() / 1000L)));

        // 用户信息
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
//        Optional<UserAccountDTO> optionalUserAccountDTO = userAccountService.getByUsername(username);
//        accessToken.getAdditionalInformation().put("user_account",optionalUserAccountDTO.get());
            accessToken.getAdditionalInformation().put("user_account", username);
        }

        // 客户端信息
        String clientId = authentication.getOAuth2Request().getClientId();

    }

}

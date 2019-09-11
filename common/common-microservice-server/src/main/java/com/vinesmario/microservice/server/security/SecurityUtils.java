package com.vinesmario.microservice.server.security;

import com.vinesmario.microservice.server.common.util.SpringContextUtil;
import com.vinesmario.microservice.server.security.model.AuthoritiesConstants;
import com.vinesmario.microservice.server.security.model.SecurityAuthority;
import com.vinesmario.microservice.server.security.model.SecurityClient;
import com.vinesmario.microservice.server.security.model.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

/**
 * Utility class for Spring Security.
 */
@Slf4j
public final class SecurityUtils {

    private SecurityUtils() {
    }

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user
     */
    public static Optional<UserDetails> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> {
                    TokenStore tokenStore = SpringContextUtil.getBean(TokenStore.class);
                    String token = ((OAuth2AuthenticationDetails) authentication.getDetails()).getTokenValue();
                    DefaultOAuth2AccessToken defaultOAuth2AccessToken = (DefaultOAuth2AccessToken) tokenStore.readAccessToken(token);
                    Map<String, Object> additionalInformation = defaultOAuth2AccessToken.getAdditionalInformation();
                    // TODO 客户信息
                    log.info(String.valueOf(additionalInformation.get("iat")));
                    log.info(String.valueOf(additionalInformation.get("user_account")));
                    // TODO 客户端信息
                    OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
                    OAuth2Request storedRequest = oAuth2Authentication.getOAuth2Request();
                    String clientId = storedRequest.getClientId();
                    return null;
                });
    }

    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise
     */
    public static boolean isAuthenticated() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> authentication.getAuthorities().stream()
                        .noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(AuthoritiesConstants.ANONYMOUS)))
                .orElse(false);
    }

    /**
     * If the current user has a specific authority (security role).
     * <p>
     * The name of this method comes from the isUserInRole() method in the Servlet API
     *
     * @param role the authority to check
     * @return true if the current user has the authority, false otherwise
     */
    public static boolean isCurrentUserInRole(String role) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> {
                    if (authentication.getPrincipal() instanceof UserDetails) {
                        SecurityUser springSecurityUser = (SecurityUser) authentication.getPrincipal();
                        return Optional.ofNullable(springSecurityUser.getCurrentAuthority().getAuthority())
                                .map(authority -> authority.equals(role))
                                .orElse(false);
                    }
                    return false;
                })
                .orElse(false);
    }

    private static String getRoleWithDefaultPrefix(String defaultRolePrefix, String role) {
        if (role == null) {
            return role;
        } else if (defaultRolePrefix != null && defaultRolePrefix.length() != 0) {
            return role.startsWith(defaultRolePrefix) ? role : defaultRolePrefix + role;
        } else {
            return role;
        }
    }
}

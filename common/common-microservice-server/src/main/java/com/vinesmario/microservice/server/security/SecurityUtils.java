package com.vinesmario.microservice.server.security;

import com.vinesmario.microservice.server.security.model.AuthoritiesConstants;
import com.vinesmario.microservice.server.security.model.SecurityAuthority;
import com.vinesmario.microservice.server.security.model.SecurityClient;
import com.vinesmario.microservice.server.security.model.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * Utility class for Spring Security.
 */
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
                    if (authentication instanceof OAuth2Authentication) {
                        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
                        String username;
                        if (authentication.getPrincipal() instanceof UserDetails) {
                            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                            username = userDetails.getUsername();
                        } else if (authentication.getPrincipal() instanceof String) {
                            username = (String) authentication.getPrincipal();
                        }
                        Collection<SecurityAuthority> authorities = (Collection<SecurityAuthority>) authentication.getAuthorities();
//                        Authentication userAuthentication = oAuth2Authentication.getUserAuthentication();
                        OAuth2Request storedRequest = oAuth2Authentication.getOAuth2Request();
                        String clientId = storedRequest.getClientId();
                        Map<String, Serializable> extensions = storedRequest.getExtensions();
//                        if (userAuthentication instanceof UsernamePasswordAuthenticationToken) {
//                            SecurityUser springSecurityUser;
//                            if (authentication.getPrincipal() instanceof SecurityUser) {
//                                springSecurityUser = (SecurityUser) authentication.getPrincipal();
//                            } else if (authentication.getPrincipal() instanceof String) {
//                                if (((OAuth2Authentication) authentication).isClientOnly()) {
//                                    return null;
//                                } else {
//                                    String username = (String) authentication.getPrincipal();
//                                    // TODO 根据username从缓存中获取，例如redis
//                                    // password是密文
//                                    springSecurityUser = new SecurityUser(username, null, authorities);
//                                }
//                            } else {
//                                return null;
//                            }
//                            SecurityClient securityClient = new SecurityClient();
//                            // 根据clientId从缓存中获取，例如redis
//                            springSecurityUser.setCurrentClient(securityClient);
//                        } else {
//                            return null;
//                        }
                    }
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

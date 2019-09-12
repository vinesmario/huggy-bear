package com.vinesmario.microservice.client.security;

import com.vinesmario.microservice.client.common.util.SpringContextUtil;
import com.vinesmario.microservice.client.security.model.SecurityClientDetails;
import com.vinesmario.microservice.client.security.model.SecurityUserDetails;
import com.vinesmario.microservice.client.uaa.dto.AuthorityDTO;
import com.vinesmario.microservice.client.uaa.dto.DepartmentDTO;
import com.vinesmario.microservice.client.uaa.dto.TenantDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.Map;
import java.util.Optional;

/**
 * Utility class for Spring Security.
 */
@Slf4j
public final class SecurityUtils {

    public final static String ADDITIONAL_INFORMATION_USER_DETAILS = "user_details";
    public final static String ADDITIONAL_INFORMATION_CLIENT_DETAILS = "client_details";

    private SecurityUtils() {
    }

    public static Optional<OAuth2AccessToken> getAccessToken() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(authentication -> {
                    String token = ((OAuth2AuthenticationDetails) authentication.getDetails()).getTokenValue();
                    TokenStore tokenStore = SpringContextUtil.getBean(TokenStore.class);
                    return tokenStore.readAccessToken(token);
                });
    }

    public static Optional<Map<String, Object>> getAdditionalInformation() {
        return getAccessToken()
                .map(accessToken -> accessToken.getAdditionalInformation());
    }

    /**
     * Get the details of the current login user.
     *
     * @return Get the user details of the current login user.
     */
    public static Optional<SecurityUserDetails> getUserDetails() {
        return getAdditionalInformation()
                .map(additionalInformation -> (SecurityUserDetails) additionalInformation.get(ADDITIONAL_INFORMATION_USER_DETAILS));
    }

    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise
     */
    public static boolean isAuthenticated() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(authentication -> authentication.isAuthenticated())
                .orElse(false);
    }

    /**
     * If the current user has a specific authority (security role).
     * <p>
     * The name of this method comes from the isUserInRole() method in the Servlet API
     *
     * @param authority the authority name to check
     * @return true if the current user has the authority, false otherwise
     */
//    public static boolean hasAuthority(String authority) {
//        return getUserDetails()
//                .map(details -> details.getAuthorities().stream()
//                        .filter(grantedAuthority -> StringUtils.equals(grantedAuthority.getAuthority(), authority))
//                        .collect(Collectors.toList())
//                        .size() > 0)
//                .get();
//    }

    /**
     * Get the details of the current client.
     *
     * @return Get the details of the current client.
     */
    public static Optional<SecurityClientDetails> getClientDetails() {
        return getAdditionalInformation()
                .map(additionalInformation -> (SecurityClientDetails) additionalInformation.get(ADDITIONAL_INFORMATION_CLIENT_DETAILS));
    }

    /**
     * Get the details of the current authority.
     *
     * @return Get the details of the current authority.
     */
    public static Optional<AuthorityDTO> getCurrentAuthority() {
        return getUserDetails()
                .map(userDetails -> userDetails.getCurrentAuthority());
    }

    public static boolean isAuthority(String authority) {
        return getUserDetails()
                .map(userDetails -> StringUtils.equals(userDetails.getCurrentAuthority().getName(), authority))
                .orElse(false);
    }

    public static Optional<TenantDTO> getGrantedTenant() {
        return getUserDetails()
                .map(userDetails -> userDetails.getGrantedTenant());
    }

}

package com.vinesmario.microservice.client.common.security;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * Client talking to an OAuth2 Authorization server token endpoint.
 *
 * @see ServiceTokenEndpointClient
 * @see OAuth2TokenEndpointClientAdapter
 * @see org.springframework.security.oauth2.provider.endpoint.TokenEndpoint
 */
public interface OAuth2TokenEndpointClient {
    /**
     * Send a password grant to the token endpoint.
     *
     * @param username the username to authenticate.
     * @param password his password.
     * @return the access token and enclosed refresh token received from the token endpoint.
     * @throws org.springframework.security.oauth2.common.exceptions.ClientAuthenticationException
     * if we cannot contact the token endpoint.
     */
    OAuth2AccessToken sendPasswordGrant(String username, String password);

    /**
     * Send a refresh_token grant to the token endpoint.
     *
     * @param refreshTokenValue the refresh token used to get new tokens.
     * @return the new access/refresh token pair.
     * @throws org.springframework.security.oauth2.common.exceptions.ClientAuthenticationException
     * if we cannot contact the token endpoint.
     */
    OAuth2AccessToken sendRefreshGrant(String refreshTokenValue);
    
    /**
     * Send a client grant to the token endpoint.
     * 
     * @return
     */
    OAuth2AccessToken sendClentCredentialsGrant();
}

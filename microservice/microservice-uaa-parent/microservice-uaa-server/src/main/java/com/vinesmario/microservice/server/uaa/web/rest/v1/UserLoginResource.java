package com.vinesmario.microservice.server.uaa.web.rest.v1;

import com.vinesmario.microservice.client.common.util.SpringContextUtil;
import com.vinesmario.microservice.client.uaa.dto.AuthorityDTO;
import com.vinesmario.microservice.server.security.model.IatTokenEnhancer;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.web.bind.annotation.*;

@Api(description = "UserAccountCRUD", tags = "UserAccountController", basePath = "/user_account")
@Slf4j
@RestController
@RequestMapping("/api/v1/user_login")
public class UserLoginResource {

    /**
     * 切换权限角色
     *
     * @param authorityId
     * @return
     */
    @GetMapping(value = "/token/change/authority", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<OAuth2AccessToken> getAccessTokenByChangeAuthority(@RequestParam Long authorityId) {
        AuthorityDTO authorityDTO = new AuthorityDTO();
        authorityDTO.setId(authorityId);
        return this.putAccessTokenByChangeAuthority(authorityDTO);
    }

    /**
     * 切换权限角色
     *
     * @return
     */
    @PutMapping(value = "/token/change/authority", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<OAuth2AccessToken> putAccessTokenByChangeAuthority(@RequestBody AuthorityDTO authorityDTO) {
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
        oAuth2Authentication.getOAuth2Request().getRequestParameters().put(IatTokenEnhancer.CURRENT_AUTHORITY_ID, String.valueOf(authorityDTO.getId()));

        AuthorizationServerEndpointsConfiguration configuration = SpringContextUtil.getBean(AuthorizationServerEndpointsConfiguration.class);
        AuthorizationServerTokenServices tokenServices = configuration.getEndpointsConfigurer().getDefaultAuthorizationServerTokenServices();
        OAuth2AccessToken accessToken = tokenServices.createAccessToken(oAuth2Authentication);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        headers.set("Content-Type", "application/json;charset=UTF-8");
        return ResponseEntity.ok()
                .headers(headers)
                .body(accessToken);
    }

}

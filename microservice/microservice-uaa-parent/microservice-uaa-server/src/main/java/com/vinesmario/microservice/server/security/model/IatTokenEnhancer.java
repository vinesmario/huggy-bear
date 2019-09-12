package com.vinesmario.microservice.server.security.model;

import com.vinesmario.microservice.client.security.SecurityUtils;
import com.vinesmario.microservice.client.security.model.SecurityClientDetails;
import com.vinesmario.microservice.client.security.model.SecurityUserDetails;
import com.vinesmario.microservice.client.uaa.dto.AuthorityDTO;
import com.vinesmario.microservice.client.uaa.dto.TenantDTO;
import com.vinesmario.microservice.server.uaa.service.UserAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Adds the standard "iat" claim to tokens so we know when they have been created.
 * This is needed for a session timeout due to inactivity (ignored in case of "remember-me").
 */
@Slf4j
@Component
public class IatTokenEnhancer implements TokenEnhancer {

    public final static String CURRENT_AUTHORITY_ID = "CURRENT_AUTHORITY_ID";
    public final static String GRANTED_TENANT_ID = "GRANTED_TENANT_ID";

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

        // TODO 客户端信息
        // TODO 从数据库中获取更多客户端信息
        String clientId = authentication.getOAuth2Request().getClientId();
        SecurityClientDetails clientDetails = new SecurityClientDetails();
        clientDetails.setClientId(clientId);
        accessToken.getAdditionalInformation().put(SecurityUtils.ADDITIONAL_INFORMATION_CLIENT_DETAILS, clientDetails);

        // TODO 用户信息
        String username = null;
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            username = userDetails.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            username = (String) authentication.getPrincipal();
        }
        // 从数据库中获取更多用户账户信息
//            Optional<UserAccountDTO> optionalUserAccountDTO = userAccountService.getByUsername(username);
//            SecurityUserDetails securityUserDetails = new SecurityUserDetails(username, "", null);
        SecurityUserDetails securityUserDetails = new SecurityUserDetails();
        securityUserDetails.setName(username);

        Map<String, Serializable> extensions = authentication.getOAuth2Request().getExtensions();
        if (!ObjectUtils.isEmpty(extensions)) {
            // TODO 用户选择切换权限角色或者机构租户（二选一）
            Long authorityId = (Long) extensions.get(CURRENT_AUTHORITY_ID);
            Long tenantId = (Long) extensions.get(GRANTED_TENANT_ID);
            if (!ObjectUtils.isEmpty(authorityId)) {
                // TODO 指定权限角色
                // 数据库中获取权限角色数据
                AuthorityDTO authorityDTO = new AuthorityDTO();
                authorityDTO.setId(authorityId);
                authorityDTO.setName("test");
                securityUserDetails.setCurrentAuthority(authorityDTO);
            } else if (!ObjectUtils.isEmpty(tenantId)) {
                // TODO 指定查看数据的机构租户
                // 数据库中获取机构租户数据
                TenantDTO tenantDTO = new TenantDTO();
                tenantDTO.setId(tenantId);
                tenantDTO.setName("name");
                securityUserDetails.setGrantedTenant(tenantDTO);
            }
        }
        securityUserDetails.setClientDetails(clientDetails);
        accessToken.getAdditionalInformation().put(SecurityUtils.ADDITIONAL_INFORMATION_USER_DETAILS, securityUserDetails);
    }

}

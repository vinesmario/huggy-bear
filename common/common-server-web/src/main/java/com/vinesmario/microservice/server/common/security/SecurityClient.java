package com.vinesmario.microservice.server.common.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.util.Assert;

import java.util.Set;

@Data
public class SecurityClient extends BaseClientDetails {

    /**
     * 资源集合
     */
    private Set<SecurityResource> resources;

}

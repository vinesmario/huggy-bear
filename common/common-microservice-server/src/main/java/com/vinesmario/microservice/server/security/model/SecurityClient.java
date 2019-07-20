package com.vinesmario.microservice.server.security.model;

import lombok.Data;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.util.Set;

@Data
public class SecurityClient extends BaseClientDetails {

    /**
     * 资源集合
     */
    private Set<SecurityResource> resources;

}

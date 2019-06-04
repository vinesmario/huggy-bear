package com.vinesmario.microservice.server.common.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Set;

@Data
public class SecurityAuthority implements GrantedAuthority {

    /**
     * 角色名称
     */
    private final String authority;
    /**
     * 客户端集合
     */
    private Set<SecurityClient> clients;
    /**
     * 资源列表
     */
    private Set<SecurityResource> resources;

    public SecurityAuthority(String authority) {
        Assert.hasText(authority, "A granted authority textual representation is required");
        this.authority = authority;
    }

    public SecurityAuthority(String authority, Set<SecurityClient> clients, Set<SecurityResource> resources) {
        Assert.hasText(authority, "A granted authority textual representation is required");
        this.authority = authority;
        this.clients = clients;
        this.resources = resources;
    }

}

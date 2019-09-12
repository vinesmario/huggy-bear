package com.vinesmario.microservice.client.security.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

@Data
public class SecurityGrantedAuthority implements GrantedAuthority {

    /**
     * 角色名称
     */
    private final String authority;

    public SecurityGrantedAuthority(String authority) {
        Assert.hasText(authority, "A granted authority textual representation is required");
        this.authority = authority;
    }

}

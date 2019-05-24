package com.vinesmario.microservice.server.common.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.List;

@Data
public class SecurityAuthority implements GrantedAuthority {

    private final String authority;
    private List<String> permissionList;

    public SecurityAuthority(String authority, List<String> permissionList) {
        Assert.hasText(authority, "A granted authority textual representation is required");
        this.authority = authority;
        this.permissionList = permissionList;
    }

}

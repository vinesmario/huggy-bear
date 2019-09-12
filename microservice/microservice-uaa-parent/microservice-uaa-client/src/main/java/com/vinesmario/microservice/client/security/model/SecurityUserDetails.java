package com.vinesmario.microservice.client.security.model;

import com.vinesmario.microservice.client.uaa.dto.AuthorityDTO;
import com.vinesmario.microservice.client.uaa.dto.TenantDTO;
import lombok.Data;

/**
 * 登录用户
 */
@Data
public class SecurityUserDetails {

    private String name;
    /**
     * 登录用户当前的客户端
     */
    private SecurityClientDetails clientDetails;
    /**
     * 登录用户在当前客户端使用的权限
     */
    private AuthorityDTO currentAuthority;
    /**
     * 登录用户在当前客户端查询数据用的租户
     */
    private TenantDTO grantedTenant;

}

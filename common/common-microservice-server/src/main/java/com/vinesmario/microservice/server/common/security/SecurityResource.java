package com.vinesmario.microservice.server.common.security;

import lombok.Data;

@Data
public class SecurityResource {

    /**
     * 类型
     */
    private Integer type;
    /**
     * 权限
     */
    private String permission;

}

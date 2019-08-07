package com.vinesmario.microservice.client.uaa.dto;

import com.vinesmario.microservice.client.common.dto.TreeDTO;

/**
 * 租户
 */
public class TenantDTO extends TreeDTO<TenantDTO, Long> {

    /**
     * 组织机构名称
     */
    private String name;

    @Override
    public String getAlertParam() {
        return name;
    }
}

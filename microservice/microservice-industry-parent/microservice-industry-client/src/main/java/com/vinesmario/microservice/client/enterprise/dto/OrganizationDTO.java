package com.vinesmario.microservice.client.enterprise.dto;

import com.vinesmario.microservice.client.common.dto.TreeDTO;

/**
 * 组织机构，树形结构
 */
public class OrganizationDTO extends TreeDTO<OrganizationDTO, Long> {

    private String name;
    /**
     * 租户ID，创建租户后，返回租户ID，再保存入库。
     */
    private Long tenantId;

    @Override
    public String getAlertParam() {
        return name;
    }
}

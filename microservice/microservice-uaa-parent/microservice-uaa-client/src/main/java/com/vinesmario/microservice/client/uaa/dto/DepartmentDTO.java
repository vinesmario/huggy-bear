package com.vinesmario.microservice.client.uaa.dto;

import com.vinesmario.microservice.client.common.dto.TreeDTO;

/**
 * 租户下属部门
 */
public class DepartmentDTO extends TreeDTO<DepartmentDTO, Long> {

    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 部门名称
     */
    private String name;

    @Override
    public String getAlertParam() {
        return null;
    }
}

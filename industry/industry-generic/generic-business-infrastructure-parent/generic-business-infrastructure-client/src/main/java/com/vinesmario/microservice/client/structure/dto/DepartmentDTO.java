package com.vinesmario.microservice.client.structure.dto;

import com.vinesmario.microservice.client.common.dto.TreeDTO;

/**
 * 部门，树形结构
 */
public class DepartmentDTO extends TreeDTO<DepartmentDTO, Long> {

    private Long organizationId;
    private String name;

    @Override
    public String getAlertParam() {
        return null;
    }
}

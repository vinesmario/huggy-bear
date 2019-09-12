package com.vinesmario.microservice.client.uaa.dto;

import com.vinesmario.microservice.client.common.dto.TreeDTO;
import lombok.Data;

import java.util.List;

/**
 * 租户
 */
@Data
public class TenantDTO extends TreeDTO<TenantDTO, Long> {

    /**
     * 组织机构名称
     */
    private String name;
    /**
     * 部门列表
     */
    private List<DepartmentDTO> departmentList;

    @Override
    public String getAlertParam() {
        return name;
    }
}

package com.vinesmario.microservice.client.enterprise.dto;

import com.vinesmario.microservice.client.common.dto.TreeDTO;

/**
 * 岗位，树形结构
 */
public class StationDTO extends TreeDTO<StationDTO, Long> {

    private Long organizationId;
    private Long departmentId;
    private String name;

    /**
     * 角色ID，创建角色后，返回角色ID，再保存入库。
     */
    private Long roleId;

    @Override
    public String getAlertParam() {
        return null;
    }
}

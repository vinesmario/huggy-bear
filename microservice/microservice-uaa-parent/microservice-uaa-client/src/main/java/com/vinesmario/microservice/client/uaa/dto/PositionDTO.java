package com.vinesmario.microservice.client.uaa.dto;

import com.vinesmario.microservice.client.common.dto.TreeDTO;

/**
 * 岗位，树形结构
 * 弱化角色的概念,岗位对应多个职责，职责是资源权限组
 */
public class PositionDTO extends TreeDTO<PositionDTO, Long> {

    private Long organizationId;
    private Long departmentId;
    private String name;
    private Integer dataScope;

    @Override
    public String getAlertParam() {
        return null;
    }
}

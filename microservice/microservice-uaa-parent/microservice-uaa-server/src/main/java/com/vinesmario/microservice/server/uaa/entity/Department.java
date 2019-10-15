package com.vinesmario.microservice.server.uaa.entity;

import com.vinesmario.microservice.server.common.entity.TreeEntity;
import lombok.Data;

/**
 * 租户下属部门
 */
@Data
public class Department extends TreeEntity<Long> {

    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 部门名称
     */
    private String name;

}

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
     * 住宿部、餐饮部、图书部、门诊部、住院部...
     */
    private String name;

}

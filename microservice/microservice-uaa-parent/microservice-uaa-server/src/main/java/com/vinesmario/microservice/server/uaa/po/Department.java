package com.vinesmario.microservice.server.uaa.po;

import com.vinesmario.microservice.server.common.po.TreePO;
import lombok.Data;

/**
 * 租户下属部门
 */
@Data
public class Department extends TreePO<Long> {

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

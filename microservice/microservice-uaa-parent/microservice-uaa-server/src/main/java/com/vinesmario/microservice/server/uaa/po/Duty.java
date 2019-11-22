package com.vinesmario.microservice.server.uaa.po;

import com.vinesmario.microservice.server.common.po.TreePO;
import lombok.Data;

/**
 * 职责
 * 岗位Position与职权Authority是多对对关系，职权Authority是资源Resource组
 * 不同职责授予不同的数据范围：
 * 1，所在机构
 * 2，所在机构及直接下属机构
 * 3，所在机构及所有下属机构；
 * 4，所在部门
 * 5，所在部门及直接下属部门
 * 6，所在部门及所有下属部门
 * 7，所在岗位
 * 8，所在岗位及直接下属岗位
 * 9，所在岗位及所有下属岗位
 * 10，所在小组
 * 11，仅自己
 */
@Data
public class Duty extends TreePO<Long> {

    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 岗位ID
     */
    private Long positionId;
    /**
     * 职权ID
     */
    private Long authorityId;
    /**
     * 数据范围
     */
    private Integer dataScope;

}

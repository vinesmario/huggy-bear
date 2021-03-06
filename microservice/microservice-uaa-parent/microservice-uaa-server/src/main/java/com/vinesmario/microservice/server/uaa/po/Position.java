package com.vinesmario.microservice.server.uaa.po;

import com.vinesmario.microservice.server.common.po.BasePO;
import lombok.Data;

/**
 * 岗位，职权Authority组
 * 树形结构
 * 弱化角色的概念，
 * 岗位决定了B端用户的身份以及衍生业务
 */
@Data
public class Position extends BasePO<Long> {

    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 部门ID
     */
    private Long departmentId;
    /**
     * 岗位名称
     */
    private String name;
    /**
     * 职业
     */
    private String profession;

}

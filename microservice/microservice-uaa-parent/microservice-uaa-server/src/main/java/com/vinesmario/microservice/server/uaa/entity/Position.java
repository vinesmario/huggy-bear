package com.vinesmario.microservice.server.uaa.entity;

import com.vinesmario.microservice.server.common.entity.BaseEntity;
import lombok.Data;

/**
 * 岗位，树形结构
 * 弱化角色的概念,岗位对应多个职责，职责是资源权限组
 */
@Data
public class Position extends BaseEntity<Long> {

    private Long TenantId;
    private Long departmentId;
    private String name;

}

package com.vinesmario.microservice.server.uaa.entity;

import com.vinesmario.microservice.server.common.entity.BaseEntity;
import lombok.Data;

/**
 * 资源组
 * 权限和资源多对对关系
 */
@Data
public class AuthorityResource extends BaseEntity<Long> {

    private Long authorityId;
    private Long resourceId;

}

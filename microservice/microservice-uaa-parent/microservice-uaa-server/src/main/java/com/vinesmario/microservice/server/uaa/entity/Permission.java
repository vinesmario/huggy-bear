package com.vinesmario.microservice.server.uaa.entity;

import com.vinesmario.microservice.server.common.entity.BaseEntity;
import lombok.Data;

/**
 * 许可
 * 职权Authority和资源Resource是多对对关系
 */
@Data
public class Permission extends BaseEntity<Long> {

    private Long authorityId;
    private Long resourceId;

}

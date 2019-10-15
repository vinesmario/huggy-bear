package com.vinesmario.microservice.server.uaa.entity;

import com.vinesmario.microservice.server.common.entity.BaseEntity;
import lombok.Data;

/**
 * 职工岗位 多对多关系
 */
@Data
public class UserAccountPosition extends BaseEntity<Long> {

    private Long userAccountId;
    private Long positionId;

}

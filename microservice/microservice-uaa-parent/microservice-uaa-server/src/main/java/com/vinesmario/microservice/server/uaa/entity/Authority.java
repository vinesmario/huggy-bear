package com.vinesmario.microservice.server.uaa.entity;

import com.vinesmario.microservice.server.common.entity.BaseEntity;
import lombok.Data;

/**
 * 权限、职责
 */
@Data
public class Authority extends BaseEntity<Long> {

    /**
     * 权限名称
     */
    private String name;

}

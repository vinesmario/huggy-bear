package com.vinesmario.microservice.server.uaa.entity;

import com.vinesmario.microservice.server.common.entity.BaseEntity;
import lombok.Data;

/**
 * 职权，资源Resource组
 */
@Data
public class Authority extends BaseEntity<Long> {

    /**
     * 职权名称
     */
    private String name;

}

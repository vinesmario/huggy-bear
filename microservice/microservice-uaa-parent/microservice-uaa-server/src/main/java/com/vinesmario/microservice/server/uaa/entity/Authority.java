package com.vinesmario.microservice.server.uaa.entity;

import com.vinesmario.microservice.server.common.entity.BaseEntity;
import lombok.Data;

@Data
public class Authority extends BaseEntity<Long> {

    /**
     * 角色名称
     */
    private String role;

}

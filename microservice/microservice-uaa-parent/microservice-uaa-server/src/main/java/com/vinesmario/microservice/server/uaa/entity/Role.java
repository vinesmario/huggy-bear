package com.vinesmario.microservice.server.uaa.entity;

import com.vinesmario.microservice.server.common.entity.BaseEntity;
import lombok.Data;

@Data
public class Role extends BaseEntity<Long> {

    /**
     * 英文名称
     */
    private String enName;
    /**
     * 中文名称
     */
    private String cnName;
    
}

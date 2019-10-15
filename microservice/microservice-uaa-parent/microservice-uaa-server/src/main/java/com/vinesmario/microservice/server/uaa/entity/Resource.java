package com.vinesmario.microservice.server.uaa.entity;

import com.vinesmario.microservice.server.common.entity.TreeEntity;
import lombok.Data;

/**
 * 资源
 */
@Data
public class Resource extends TreeEntity<Long> {

    /**
     * 类型
     */
    private Integer type;
    /**
     * 许可
     */
    private String permission;

}

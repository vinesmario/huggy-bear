package com.vinesmario.microservice.server.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Base abstract class for entities which will
 * Create new records
 * Retrieve existing records
 * Update existing records
 * Delete existing records.
 */
@Data
public class TreeEntity<PK extends Serializable> extends BaseEntity {

    /**
     *
     */
    private PK parentId;
    /**
     *
     */
    private String parentIds;
    /**
     * 等级
     */
    private Integer level;
    /**
     * 排序
     */
    private Integer sortIndex;

}

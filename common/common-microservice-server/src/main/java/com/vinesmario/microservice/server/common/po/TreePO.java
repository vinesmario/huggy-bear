package com.vinesmario.microservice.server.common.po;

import lombok.Data;

import java.io.Serializable;

/**
 * Base abstract class for entities which will
 * Create new records
 * Read existing records
 * Update existing records
 * Delete existing records.
 */
@Data
public class TreePO<PK extends Serializable> extends BasePO {

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

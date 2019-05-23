package com.vinesmario.microservice.server.common.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Base abstract class for entities which will
 * Create new records
 * Read existing records
 * Update existing records
 * Delete existing records.
 */
@Data
@Accessors(chain = true)
public class BaseEntity<PK extends Serializable> {

    /**
     * ID，主键
     */
    private PK id;
    /**
     * 备注
     */
    private String memo;
    /**
     * 删除标识 0-否；1-是
     */
    private Byte deleted;
    /**
     * 创建者
     */
    private Long createdBy;
    /**
     * 创建时间
     */
    private LocalDateTime createdDate;
    /**
     * 最后修改者
     */
    private Long lastModifiedBy;
    /**
     * 最后修改时间
     */
    private LocalDateTime lastModifiedDate;
}

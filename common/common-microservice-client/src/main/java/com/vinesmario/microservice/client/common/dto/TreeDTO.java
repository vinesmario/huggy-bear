package com.vinesmario.microservice.client.common.dto;

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
public abstract class TreeDTO<T extends TreeDTO, PK extends Serializable> extends BaseDTO {

    private PK parentId;
    private T parent;

}

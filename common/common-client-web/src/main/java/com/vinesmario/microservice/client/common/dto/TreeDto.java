package com.vinesmario.microservice.client.common.dto;

import lombok.Data;

/**
 * Base abstract class for entities which will
 * Create new records
 * Read existing records
 * Update existing records
 * Delete existing records.
 */
@Data
public class TreeDto<T extends TreeDto> extends BaseDto<Long> {

    private Long parentId;
    private T parent;

}

package com.vinesmario.microservice.client.uaa.dto;

import com.vinesmario.microservice.client.common.dto.BaseDto;
import lombok.Data;

@Data
public class RoleDto extends BaseDto<Long> {

    /**
     * 英文名称
     */
    private String enName;
    /**
     * 中文名称
     */
    private String cnName;

}

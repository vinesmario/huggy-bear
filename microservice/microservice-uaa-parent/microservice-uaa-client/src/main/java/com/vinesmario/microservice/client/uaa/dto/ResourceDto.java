package com.vinesmario.microservice.client.uaa.dto;

import com.vinesmario.microservice.client.common.dto.TreeDto;
import lombok.Data;

@Data
public class ResourceDto extends TreeDto<ResourceDto> {

    private Integer type;
    private String permission;

}

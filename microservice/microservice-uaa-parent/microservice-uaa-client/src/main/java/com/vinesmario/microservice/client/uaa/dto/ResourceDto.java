package com.vinesmario.microservice.client.uaa.dto;

import com.vinesmario.microservice.client.common.dto.TreeDto;
import lombok.Data;

@Data
public class ResourceDto extends TreeDto<ResourceDto> {

    /**
     * 类型
     */
    private Integer type;
    /**
     * 权限
     */
    private String permission;

    @Override
    public String getAlertParam() {
        return permission;
    }
}

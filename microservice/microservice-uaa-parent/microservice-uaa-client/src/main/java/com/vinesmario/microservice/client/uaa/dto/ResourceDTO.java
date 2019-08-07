package com.vinesmario.microservice.client.uaa.dto;

import com.vinesmario.microservice.client.common.dto.TreeDTO;
import lombok.Data;

@Data
public class ResourceDTO extends TreeDTO<ResourceDTO, Long> {

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

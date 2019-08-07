package com.vinesmario.microservice.client.enterprise.dto;

import com.vinesmario.microservice.client.common.dto.BaseDTO;

/**
 * 小组
 */
public class TeamDTO extends BaseDTO<Long> {
    
    private String name;

    @Override
    public String getAlertParam() {
        return name;
    }
}

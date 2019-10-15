package com.vinesmario.microservice.client.entity.dto;

import com.vinesmario.microservice.client.common.dto.BaseDTO;

/**
 * 大学
 */
public class UniversityDTO extends BaseDTO<Long> {

    private String name;

    @Override
    public String getAlertParam() {
        return name;
    }
}

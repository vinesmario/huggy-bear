package com.vinesmario.microservice.client.entity.dto;

import com.vinesmario.microservice.client.common.dto.BaseDTO;

/**
 * 学校，指小学、中学
 */
public class SchoolDTO extends BaseDTO<Long> {

    private String name;

    @Override
    public String getAlertParam() {
        return name;
    }
}

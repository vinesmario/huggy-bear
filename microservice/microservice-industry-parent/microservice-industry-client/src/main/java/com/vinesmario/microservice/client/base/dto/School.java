package com.vinesmario.microservice.client.base.dto;

import com.vinesmario.microservice.client.common.dto.BaseDTO;

/**
 * 学校，指小学、中学
 */
public class School extends BaseDTO<Long> {

    private String name;

    @Override
    public String getAlertParam() {
        return name;
    }
}

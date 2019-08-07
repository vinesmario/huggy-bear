package com.vinesmario.microservice.client.base.dto;

import com.vinesmario.microservice.client.common.dto.BaseDTO;

/**
 * 大学
 */
public class University extends BaseDTO<Long> {

    private String name;

    @Override
    public String getAlertParam() {
        return name;
    }
}

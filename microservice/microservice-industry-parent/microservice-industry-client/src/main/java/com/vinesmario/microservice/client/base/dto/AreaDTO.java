package com.vinesmario.microservice.client.base.dto;

import com.vinesmario.microservice.client.common.dto.TreeDTO;

/**
 * 行政区划
 */
public class AreaDTO extends TreeDTO<AreaDTO> {

    /**
     * 名称
     */
    private String name;
    private String code;
    private String level;
    private String address;

    @Override
    public String getAlertParam() {
        return name;
    }

}

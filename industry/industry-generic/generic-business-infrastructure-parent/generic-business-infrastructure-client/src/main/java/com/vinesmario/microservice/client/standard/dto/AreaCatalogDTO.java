package com.vinesmario.microservice.client.standard.dto;

import com.vinesmario.microservice.client.common.dto.TreeDTO;

/**
 * 行政区划
 * GB T 2260-2007
 */
public class AreaCatalogDTO extends TreeDTO<AreaCatalogDTO, Long> {

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

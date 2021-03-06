package com.vinesmario.microservice.client.standard.dto;

import com.vinesmario.microservice.client.common.dto.BaseDTO;

/**
 * 字典目录
 */
public class DictionaryCatalogDTO extends BaseDTO<Long> {

    /**
     * 名称
     */
    private String name;
    /**
     * 代码
     */
    private String code;
    /**
     * 值
     */
    private String value;

    @Override
    public String getAlertParam() {
        return name;
    }

}

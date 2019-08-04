package com.vinesmario.microservice.client.base.dto;

import com.vinesmario.microservice.client.common.dto.BaseDTO;

/**
 * 字典
 */
public class DictionaryDTO extends BaseDTO<Long> {

    /**
     * 目录ID
     */
    private Long catalogId;
    /**
     * 目录代码
     */
    private String catalogCode;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 键
     */
    private String key;
    /**
     * 值
     */
    private String value;

    @Override
    public String getAlertParam() {
        return key;
    }
}

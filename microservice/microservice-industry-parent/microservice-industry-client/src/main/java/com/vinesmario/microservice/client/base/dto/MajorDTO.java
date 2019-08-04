package com.vinesmario.microservice.client.base.dto;

import com.vinesmario.microservice.client.common.dto.TreeDTO;

/**
 * 专业
 */
public class MajorDTO extends TreeDTO<MajorDTO> {

    /**
     * 名称
     */
    private String name;
    /**
     * 代码
     */
    private String code;
    /**
     * 等级
     */
    private String level;

    @Override
    public String getAlertParam() {
        return name;
    }
}

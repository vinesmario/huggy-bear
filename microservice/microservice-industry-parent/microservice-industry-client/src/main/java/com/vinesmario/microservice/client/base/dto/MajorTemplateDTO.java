package com.vinesmario.microservice.client.base.dto;

import com.vinesmario.microservice.client.common.dto.TreeDTO;

/**
 * 专业目录
 */
public class MajorTemplateDTO extends TreeDTO<MajorTemplateDTO, Long> {

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

package com.vinesmario.microservice.client.demo;

import com.vinesmario.microservice.client.common.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author
 * @date
 */
@ApiModel(value = "SupportDemoDTO", description = "SupportDemoDTO")
@Data
public class SupportDemoDTO extends BaseDTO<Long> {

    /**
     * 用户名
     */
    private String name;

    @Override
    public String getAlertParam() {
        return name;
    }
}
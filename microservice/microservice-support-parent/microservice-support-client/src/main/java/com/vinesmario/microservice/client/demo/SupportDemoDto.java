package com.vinesmario.microservice.client.demo;

import com.vinesmario.microservice.client.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author
 * @date
 */
@ApiModel(value = "SupportDemoDto", description = "SupportDemoDto")
@Data
public class SupportDemoDto extends BaseDto<Long> {

    /**
     * 用户名
     */
    private String name;

    @Override
    public String getAlertParam() {
        return name;
    }
}
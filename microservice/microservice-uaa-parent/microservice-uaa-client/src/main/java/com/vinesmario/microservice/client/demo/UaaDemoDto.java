package com.vinesmario.microservice.client.demo;

import com.vinesmario.microservice.client.common.dto.BaseDto;
import com.vinesmario.microservice.client.uaa.dto.AuthorityDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author
 * @date
 */
@ApiModel(value = "UaaDemoDto", description = "UaaDemoDto")
@Data
public class UaaDemoDto extends BaseDto<Long> {

    /**
     * 用户名
     */
    private String name;

    @Override
    public String getAlertParam() {
        return name;
    }
}
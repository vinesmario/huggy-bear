package com.vinesmario.microservice.client.demo;

import com.vinesmario.microservice.client.common.dto.BaseDTO;
import com.vinesmario.microservice.client.uaa.dto.AuthorityDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author
 * @date
 */
@ApiModel(value = "UaaDemoDTO", description = "UaaDemoDTO")
@Data
public class UaaDemoDTO extends BaseDTO<Long> {

    /**
     * 用户名
     */
    private String name;

    @Override
    public String getAlertParam() {
        return name;
    }
}
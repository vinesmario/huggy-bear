package com.vinesmario.microservice.client.uaa.dto;

import com.vinesmario.microservice.client.common.dto.BaseDTO;
import lombok.Data;

import java.util.List;

@Data
public class AuthorityDTO extends BaseDTO<Long> {

    /**
     * 角色名称
     */
    private String name;
    /**
     * 可访问、可操作资源
     */
    private List<ResourceDTO> resourceList;

    @Override
    public String getAlertParam() {
        return name;
    }
}

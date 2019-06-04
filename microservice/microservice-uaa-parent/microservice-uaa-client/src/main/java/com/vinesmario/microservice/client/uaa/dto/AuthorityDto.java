package com.vinesmario.microservice.client.uaa.dto;

import com.vinesmario.microservice.client.common.dto.BaseDto;
import lombok.Data;

import java.util.List;

@Data
public class AuthorityDto extends BaseDto<Long> {

    /**
     * 角色名称
     */
    private String role;
    /**
     * 主要角色，登录时默认角色
     */
    private Byte major;

    /**
     * 可访问、可操作资源
     */
    private List<ResourceDto> resourceList;

}

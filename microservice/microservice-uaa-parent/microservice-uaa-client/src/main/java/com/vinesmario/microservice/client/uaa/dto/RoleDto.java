package com.vinesmario.microservice.client.uaa.dto;

import com.vinesmario.microservice.client.common.dto.BaseDto;
import lombok.Data;

import java.util.List;

@Data
public class RoleDto extends BaseDto<Long> {

    /**
     * 英文名称
     */
    private String enName;
    /**
     * 中文名称
     */
    private String cnName;
    /**
     * 主要角色，登录时默认角色
     */
    private Byte major;

    private List<ResourceDto> resourceList;

}

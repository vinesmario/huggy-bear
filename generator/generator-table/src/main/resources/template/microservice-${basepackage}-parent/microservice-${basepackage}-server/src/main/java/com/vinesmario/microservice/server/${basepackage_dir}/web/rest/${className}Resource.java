<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package com.vinesmario.microservice.server.${basepackage}.web.rest;

import com.vinesmario.microservice.server.common.web.rest.BaseResource;
{basepackage}.web.feign.${className}Client;
import com.vinesmario.microservice.client.${basepackage}.dto.${className}Dto;
{basepackage}.dto.condition.${className}ConditionDto;
import com.vinesmario.microservice.server.${basepackage}.service.${className}Service;
import io.swagger.annotations.Api;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * @date
 */

@Api(description = "${className}CRUD", tags = "${className}Controller", basePath = "/${classNameLower}s")
@Data
@RestController
@RequestMapping("/api/v1/${table.sqlName}")
public class ${className}Resource extends BaseResource<${className}Dto, ${className}ConditionDto, Long>{

    @Autowired
    private ${className}Service service;

    @Override
    public void preConditionDto(${className}ConditionDto queryDto) {

    }

}

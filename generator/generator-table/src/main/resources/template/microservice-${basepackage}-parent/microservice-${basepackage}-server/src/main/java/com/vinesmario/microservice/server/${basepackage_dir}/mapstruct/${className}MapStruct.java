<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package com.vinesmario.microservice.server.${basepackage}.mapstruct;

import com.vinesmario.microservice.server.common.mapstruct.BaseMapStruct;
import com.vinesmario.microservice.client.${basepackage}.dto.${className}Dto;
import com.vinesmario.microservice.server.${basepackage}.entity.${className};
import org.mapstruct.Mapper;

/**
 * @author
 * @date
 */
@Mapper(componentModel = "spring")
public interface ${className}MapStruct extends BaseMapStruct<${className}, ${className}Dto> {

}

<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.mapstruct;

import com.vinesmario.microservice.server.common.mapstruct.BaseMapStruct;
{basepackage}.dto.${className}Dto;
{basepackage}.entity.${className};
import org.mapstruct.Mapper;

/**
 * @author
 * @date
 */

@Mapper(componentModel = "spring")
public interface ${className}MapStruct extends BaseMapStruct<${className}, ${className}Dto> {

}

<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package com.vinesmario.microservice.server.${basepackage}.mapper;

import com.vinesmario.microservice.server.common.persistence.mybatis.mapper.CrudMapper;
import com.vinesmario.microservice.server.${basepackage}.entity.${className};

/**
 * @author
 * @date
 */

public interface ${className}Mapper extends CrudMapper<${className}, Long>{

}

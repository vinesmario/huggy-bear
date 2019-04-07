<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.mapper;

import com.vinesmario.microservice.server.common.persistence.mybatis.mapper.CrudMapper;
{basepackage}.entity.${className};

/**
 * @author
 * @date
 */

public interface ${className}Mapper extends CrudMapper<${className}, Long>{

}

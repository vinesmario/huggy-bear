<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.service;

import com.vinesmario.microservice.client.common.dto.condition.ConditionDto;
import com.vinesmario.microservice.server.common.persistence.mybatis.BaseExample;
import com.vinesmario.microservice.server.common.service.mybatis.BaseService;
import com.vinesmario.microservice.client.${basepackage}.dto.${className}Dto;
{basepackage}.dto.condition.${className}ConditionDto;
{basepackage}.entity.${className};
import com.vinesmario.microservice.server.${basepackage}.mapper.${className}Mapper;
{basepackage}.mapstruct.${className}MapStruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

/**
 * @author maodipu
 * @date 2018-01-18
 */
@Data
@Service
public class ${className}Service extends BaseService<${className}Dto, ${className}, Long>{

	@Autowired
	private ${className}Mapper mapper;
	@Autowired
	private ${className}MapStructImpl mapStruct;

	public BaseExample fromConditionDto2Example(ConditionDto conditionDto) {
		BaseExample example = new BaseExample();
		BaseExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(YES_NO_N);

		if (!ObjectUtils.isEmpty(conditionDto)) {
			${className}ConditionDto ${classNameLower}ConditionDto = (${className}ConditionDto) conditionDto;
			if (!ObjectUtils.isEmpty(${classNameLower}ConditionDto.getId())) {
				criteria.andIdEqualTo(${classNameLower}ConditionDto.getId());
			}
			if (!CollectionUtils.isEmpty(${classNameLower}ConditionDto.getIds())) {
                criteria.andIdIn(${classNameLower}ConditionDto.getIds());
            }
		}
		return example;
	}

  
}

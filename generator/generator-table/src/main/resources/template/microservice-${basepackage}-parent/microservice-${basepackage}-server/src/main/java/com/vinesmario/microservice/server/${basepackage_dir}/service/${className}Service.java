<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.service;

import com.vinesmario.common.constant.DictConstant;
import com.vinesmario.microservice.client.common.dto.condition.ConditionDto;
import com.vinesmario.microservice.client.${basepackage}.dto.condition.${className}ConditionDto;
import com.vinesmario.microservice.client.${basepackage}.dto.${className}Dto;
import com.vinesmario.microservice.server.common.persistence.mybatis.BaseExample;
import com.vinesmario.microservice.server.common.service.mybatis.impl.BaseService;
import com.vinesmario.microservice.server.${basepackage}.entity.${className};
import com.vinesmario.microservice.server.${basepackage}.mapper.${className}Mapper;
import com.vinesmario.microservice.server.${basepackage}.mapstruct.${className}MapStruct;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

/**
 * @author maodipu
 * @date 2018-01-18
 */
@Slf4j
@Service
@Transactional
public class ${className}Service extends BaseService<${className}Dto, ${className}, Long>{

	private final ${className}Mapper mapper;
	private final ${className}MapStruct mapStruct;

	public ${className}Service(${className}Mapper mapper,
								@Qualifier("${classNameLower}MapStructImpl") ${className}MapStruct mapStruct) {
		super(mapper, mapStruct);
		this.mapper = mapper;
		this.mapStruct = mapStruct;
	}

	public BaseExample fromConditionDto2Example(ConditionDto conditionDto) {
		BaseExample example = new BaseExample();
		BaseExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(DictConstant.BYTE_YES_NO_N);

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

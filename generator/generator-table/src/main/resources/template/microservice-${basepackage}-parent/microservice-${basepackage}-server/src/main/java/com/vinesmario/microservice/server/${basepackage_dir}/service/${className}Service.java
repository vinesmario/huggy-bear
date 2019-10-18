<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.service;

import com.vinesmario.common.constant.DictConstant;
import com.vinesmario.microservice.client.common.dto.condition.ConditionDTO;
import com.vinesmario.microservice.client.${basepackage}.dto.condition.${className}ConditionDTO;
import com.vinesmario.microservice.client.${basepackage}.dto.${className}DTO;
import com.vinesmario.microservice.server.common.persistence.mybatis.BaseExample;
import com.vinesmario.microservice.server.common.service.mybatis.impl.BaseService;
import com.vinesmario.microservice.server.${basepackage}.mapper.${className}Mapper;
import com.vinesmario.microservice.server.${basepackage}.mapstruct.${className}MapStruct;
import com.vinesmario.microservice.server.${basepackage}.po.${className};

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
public class ${className}Service extends BaseService<${className}DTO, ${className}, Long>{

	private final ${className}Mapper mapper;
	private final ${className}MapStruct mapStruct;

	public ${className}Service(${className}Mapper mapper,
								@Qualifier("${classNameLower}MapStructImpl") ${className}MapStruct mapStruct) {
		super(mapper, mapStruct);
		this.mapper = mapper;
		this.mapStruct = mapStruct;
	}

	public BaseExample fromConditionDTO2Example(ConditionDTO conditionDTO) {
		BaseExample example = new BaseExample();
		BaseExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(DictConstant.BYTE_YES_NO_N);

		if (!ObjectUtils.isEmpty(conditionDTO)) {
			${className}ConditionDTO ${classNameLower}ConditionDTO = (${className}ConditionDTO) conditionDTO;
			if (!ObjectUtils.isEmpty(${classNameLower}ConditionDTO.getId())) {
				criteria.andIdEqualTo(${classNameLower}ConditionDTO.getId());
			}
			if (!CollectionUtils.isEmpty(${classNameLower}ConditionDTO.getIds())) {
                criteria.andIdIn(${classNameLower}ConditionDTO.getIds());
            }
		}
		return example;
	}

  
}

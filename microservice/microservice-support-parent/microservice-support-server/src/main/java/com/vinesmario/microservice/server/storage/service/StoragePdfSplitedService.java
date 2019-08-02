package com.vinesmario.microservice.server.storage.service;

import com.vinesmario.common.constant.DictConstant;
import com.vinesmario.microservice.client.common.dto.condition.ConditionDTO;
import com.vinesmario.microservice.client.storage.dto.condition.StoragePdfSplitedConditionDTO;
import com.vinesmario.microservice.client.storage.dto.StoragePdfSplitedDTO;
import com.vinesmario.microservice.server.common.persistence.mybatis.BaseExample;
import com.vinesmario.microservice.server.common.service.mybatis.impl.BaseService;
import com.vinesmario.microservice.server.storage.entity.StoragePdf;
import com.vinesmario.microservice.server.storage.entity.StoragePdfSplited;
import com.vinesmario.microservice.server.storage.mapper.StoragePdfSplitedMapper;
import com.vinesmario.microservice.server.storage.mapstruct.StoragePdfSplitedMapStruct;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

/**
 * @author maodipu
 * @date 2018-01-18
 */
@Slf4j
@Service
@Transactional
public class StoragePdfSplitedService extends BaseService<StoragePdfSplitedDTO, StoragePdfSplited, Long>{

	private final StoragePdfSplitedMapper mapper;
	private final StoragePdfSplitedMapStruct mapStruct;

	public StoragePdfSplitedService(StoragePdfSplitedMapper mapper,
								@Qualifier("storagePdfSplitedMapStructImpl") StoragePdfSplitedMapStruct mapStruct) {
		super(mapper, mapStruct);
		this.mapper = mapper;
		this.mapStruct = mapStruct;
	}

	public BaseExample fromConditionDTO2Example(ConditionDTO conditionDTO) {
		BaseExample example = new BaseExample();
		BaseExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(DictConstant.BYTE_YES_NO_N);

		if (!ObjectUtils.isEmpty(conditionDTO)) {
			StoragePdfSplitedConditionDTO storagePdfSplitedConditionDTO = (StoragePdfSplitedConditionDTO) conditionDTO;
			if (!ObjectUtils.isEmpty(storagePdfSplitedConditionDTO.getId())) {
				criteria.andIdEqualTo(storagePdfSplitedConditionDTO.getId());
			}
			if (!CollectionUtils.isEmpty(storagePdfSplitedConditionDTO.getIds())) {
                criteria.andIdIn(storagePdfSplitedConditionDTO.getIds());
            }
		}
		return example;
	}

	@Transactional(readOnly = true)
	public Optional<StoragePdfSplitedDTO> getByUuid(String uuid) {
		return Optional.ofNullable(mapStruct.fromEntity2DTO(mapper.selectByUuid(uuid)));
	}
  
}

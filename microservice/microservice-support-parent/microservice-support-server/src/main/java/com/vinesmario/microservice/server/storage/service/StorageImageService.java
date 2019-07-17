package com.vinesmario.microservice.server.storage.service;

import com.vinesmario.common.constant.DictConstant;
import com.vinesmario.microservice.client.common.dto.condition.ConditionDto;
import com.vinesmario.microservice.client.storage.dto.StorageImageDto;
import com.vinesmario.microservice.client.storage.dto.condition.StorageImageConditionDto;
import com.vinesmario.microservice.server.common.persistence.mybatis.BaseExample;
import com.vinesmario.microservice.server.common.service.mybatis.impl.BaseService;
import com.vinesmario.microservice.server.storage.entity.StorageImage;
import com.vinesmario.microservice.server.storage.mapper.StorageImageMapper;
import com.vinesmario.microservice.server.storage.mapstruct.StorageImageMapStruct;
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
public class StorageImageService extends BaseService<StorageImageDto, StorageImage, Long>{

	private final StorageImageMapper mapper;
	private final StorageImageMapStruct mapStruct;

	public StorageImageService(StorageImageMapper mapper,
								@Qualifier("storageImageMapStructImpl") StorageImageMapStruct mapStruct) {
		super(mapper, mapStruct);
		this.mapper = mapper;
		this.mapStruct = mapStruct;
	}

	public BaseExample fromConditionDto2Example(ConditionDto conditionDto) {
		BaseExample example = new BaseExample();
		BaseExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(DictConstant.BYTE_YES_NO_N);

		if (!ObjectUtils.isEmpty(conditionDto)) {
			StorageImageConditionDto storageImageConditionDto = (StorageImageConditionDto) conditionDto;
			if (!ObjectUtils.isEmpty(storageImageConditionDto.getId())) {
				criteria.andIdEqualTo(storageImageConditionDto.getId());
			}
			if (!CollectionUtils.isEmpty(storageImageConditionDto.getIds())) {
                criteria.andIdIn(storageImageConditionDto.getIds());
            }
		}
		return example;
	}

    @Transactional(readOnly = true)
    public Optional<StorageImageDto> getByUuid(String uuid) {
        return Optional.ofNullable(mapStruct.fromEntity2Dto(mapper.selectByUuid(uuid)));
    }
}

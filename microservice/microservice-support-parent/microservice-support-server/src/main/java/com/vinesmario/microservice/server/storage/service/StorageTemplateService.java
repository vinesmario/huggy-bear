package com.vinesmario.microservice.server.storage.service;

import com.vinesmario.common.constant.DictConstant;
import com.vinesmario.microservice.client.common.dto.condition.ConditionDto;
import com.vinesmario.microservice.client.storage.dto.StoragePdfSplitedDto;
import com.vinesmario.microservice.client.storage.dto.condition.StorageTemplateConditionDto;
import com.vinesmario.microservice.client.storage.dto.StorageTemplateDto;
import com.vinesmario.microservice.server.common.persistence.mybatis.BaseExample;
import com.vinesmario.microservice.server.common.service.mybatis.impl.BaseService;
import com.vinesmario.microservice.server.storage.entity.StorageTemplate;
import com.vinesmario.microservice.server.storage.mapper.StorageTemplateMapper;
import com.vinesmario.microservice.server.storage.mapstruct.StorageTemplateMapStruct;

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
public class StorageTemplateService extends BaseService<StorageTemplateDto, StorageTemplate, Long> {

    private final StorageTemplateMapper mapper;
    private final StorageTemplateMapStruct mapStruct;

    public StorageTemplateService(StorageTemplateMapper mapper,
                                  @Qualifier("storageTemplateMapStructImpl") StorageTemplateMapStruct mapStruct) {
        super(mapper, mapStruct);
        this.mapper = mapper;
        this.mapStruct = mapStruct;
    }

    public BaseExample fromConditionDto2Example(ConditionDto conditionDto) {
        BaseExample example = new BaseExample();
        BaseExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(DictConstant.BYTE_YES_NO_N);

        if (!ObjectUtils.isEmpty(conditionDto)) {
            StorageTemplateConditionDto storageTemplateConditionDto = (StorageTemplateConditionDto) conditionDto;
            if (!ObjectUtils.isEmpty(storageTemplateConditionDto.getId())) {
                criteria.andIdEqualTo(storageTemplateConditionDto.getId());
            }
            if (!CollectionUtils.isEmpty(storageTemplateConditionDto.getIds())) {
                criteria.andIdIn(storageTemplateConditionDto.getIds());
            }
        }
        return example;
    }

    @Transactional(readOnly = true)
    public Optional<StorageTemplateDto> getByUuid(String uuid) {
        return Optional.ofNullable(mapStruct.fromEntity2Dto(mapper.selectByUuid(uuid)));
    }

}

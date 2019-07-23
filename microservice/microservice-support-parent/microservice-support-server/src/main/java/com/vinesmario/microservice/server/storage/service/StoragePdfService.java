package com.vinesmario.microservice.server.storage.service;

import com.vinesmario.common.constant.DictConstant;
import com.vinesmario.microservice.client.common.dto.condition.ConditionDto;
import com.vinesmario.microservice.client.storage.dto.condition.StoragePdfConditionDto;
import com.vinesmario.microservice.client.storage.dto.StoragePdfDto;
import com.vinesmario.microservice.server.common.persistence.mybatis.BaseExample;
import com.vinesmario.microservice.server.common.service.mybatis.impl.BaseService;
import com.vinesmario.microservice.server.storage.entity.StoragePdf;
import com.vinesmario.microservice.server.storage.mapper.StoragePdfMapper;
import com.vinesmario.microservice.server.storage.mapstruct.StoragePdfMapStruct;

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
public class StoragePdfService extends BaseService<StoragePdfDto, StoragePdf, Long> {

    private final StoragePdfMapper mapper;
    private final StoragePdfMapStruct mapStruct;

    public StoragePdfService(StoragePdfMapper mapper,
                             @Qualifier("storagePdfMapStructImpl") StoragePdfMapStruct mapStruct) {
        super(mapper, mapStruct);
        this.mapper = mapper;
        this.mapStruct = mapStruct;
    }

    public BaseExample fromConditionDto2Example(ConditionDto conditionDto) {
        BaseExample example = new BaseExample();
        BaseExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(DictConstant.BYTE_YES_NO_N);

        if (!ObjectUtils.isEmpty(conditionDto)) {
            StoragePdfConditionDto storagePdfConditionDto = (StoragePdfConditionDto) conditionDto;
            if (!ObjectUtils.isEmpty(storagePdfConditionDto.getId())) {
                criteria.andIdEqualTo(storagePdfConditionDto.getId());
            }
            if (!CollectionUtils.isEmpty(storagePdfConditionDto.getIds())) {
                criteria.andIdIn(storagePdfConditionDto.getIds());
            }
        }
        return example;
    }

    @Transactional(readOnly = true)
    public Optional<StoragePdfDto> getByUuid(String uuid) {
        return Optional.ofNullable(mapStruct.fromEntity2Dto(mapper.selectByUuid(uuid)));
    }

}

package com.vinesmario.microservice.server.storage.service;

import com.vinesmario.common.constant.DictConstant;
import com.vinesmario.microservice.client.common.dto.condition.ConditionDto;
import com.vinesmario.microservice.client.storage.dto.condition.StorageExcelConditionDto;
import com.vinesmario.microservice.client.storage.dto.StorageExcelDto;
import com.vinesmario.microservice.server.common.persistence.mybatis.BaseExample;
import com.vinesmario.microservice.server.common.service.mybatis.impl.BaseService;
import com.vinesmario.microservice.server.storage.entity.StorageExcel;
import com.vinesmario.microservice.server.storage.mapper.StorageExcelMapper;
import com.vinesmario.microservice.server.storage.mapstruct.StorageExcelMapStruct;

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
public class StorageExcelService extends BaseService<StorageExcelDto, StorageExcel, Long> {

    private final StorageExcelMapper mapper;
    private final StorageExcelMapStruct mapStruct;

    public StorageExcelService(StorageExcelMapper mapper,
                               @Qualifier("storageExcelMapStructImpl") StorageExcelMapStruct mapStruct) {
        super(mapper, mapStruct);
        this.mapper = mapper;
        this.mapStruct = mapStruct;
    }

    public BaseExample fromConditionDto2Example(ConditionDto conditionDto) {
        BaseExample example = new BaseExample();
        BaseExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(DictConstant.BYTE_YES_NO_N);

        if (!ObjectUtils.isEmpty(conditionDto)) {
            StorageExcelConditionDto storageExcelConditionDto = (StorageExcelConditionDto) conditionDto;
            if (!ObjectUtils.isEmpty(storageExcelConditionDto.getId())) {
                criteria.andIdEqualTo(storageExcelConditionDto.getId());
            }
            if (!CollectionUtils.isEmpty(storageExcelConditionDto.getIds())) {
                criteria.andIdIn(storageExcelConditionDto.getIds());
            }
        }
        return example;
    }

    @Transactional(readOnly = true)
    public Optional<StorageExcelDto> getByUuid(String uuid) {
        return Optional.ofNullable(mapStruct.fromEntity2Dto(mapper.selectByUuid(uuid)));
    }

}

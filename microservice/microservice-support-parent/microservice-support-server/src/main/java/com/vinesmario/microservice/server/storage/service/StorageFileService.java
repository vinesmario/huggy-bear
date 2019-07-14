package com.vinesmario.microservice.server.storage.service;

import com.vinesmario.common.constant.DictConstant;
import com.vinesmario.microservice.client.common.dto.condition.ConditionDto;
import com.vinesmario.microservice.client.storage.dto.StorageFileDto;
import com.vinesmario.microservice.client.storage.dto.condition.StorageFileConditionDto;
import com.vinesmario.microservice.server.common.persistence.mybatis.BaseExample;
import com.vinesmario.microservice.server.common.service.mybatis.impl.BaseService;
import com.vinesmario.microservice.server.storage.entity.StorageFile;
import com.vinesmario.microservice.server.storage.mapper.StorageFileMapper;
import com.vinesmario.microservice.server.storage.mapstruct.StorageFileMapStruct;
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
public class StorageFileService extends BaseService<StorageFileDto, StorageFile, Long> {

    private final StorageFileMapper mapper;
    private final StorageFileMapStruct mapStruct;

    public StorageFileService(StorageFileMapper mapper,
                              @Qualifier("storageFileMapStructImpl") StorageFileMapStruct mapStruct) {
        super(mapper, mapStruct);
        this.mapper = mapper;
        this.mapStruct = mapStruct;
    }

    public BaseExample fromConditionDto2Example(ConditionDto conditionDto) {
        BaseExample example = new BaseExample();
        BaseExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(DictConstant.BYTE_YES_NO_N);

        if (!ObjectUtils.isEmpty(conditionDto)) {
            StorageFileConditionDto storageFileConditionDto = (StorageFileConditionDto) conditionDto;
            if (!ObjectUtils.isEmpty(storageFileConditionDto.getId())) {
                criteria.andIdEqualTo(storageFileConditionDto.getId());
            }
            if (!CollectionUtils.isEmpty(storageFileConditionDto.getIds())) {
                criteria.andIdIn(storageFileConditionDto.getIds());
            }
        }
        return example;
    }


    @Transactional(readOnly = true)
    public Optional<StorageFileDto> getByUuid(String uuid) {
        return Optional.ofNullable(mapStruct.fromEntity2Dto(mapper.selectByUuid(uuid)));
    }
}

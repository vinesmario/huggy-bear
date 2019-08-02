package com.vinesmario.microservice.server.storage.service;

import com.vinesmario.common.constant.DictConstant;
import com.vinesmario.microservice.client.common.dto.condition.ConditionDTO;
import com.vinesmario.microservice.client.storage.dto.StorageFileDTO;
import com.vinesmario.microservice.client.storage.dto.condition.StorageFileConditionDTO;
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
@Transactional
public class StorageFileService extends BaseService<StorageFileDTO, StorageFile, Long> {

    private final StorageFileMapper mapper;
    private final StorageFileMapStruct mapStruct;

    public StorageFileService(StorageFileMapper mapper,
                              @Qualifier("storageFileMapStructImpl") StorageFileMapStruct mapStruct) {
        super(mapper, mapStruct);
        this.mapper = mapper;
        this.mapStruct = mapStruct;
    }

    public BaseExample fromConditionDTO2Example(ConditionDTO conditionDTO) {
        BaseExample example = new BaseExample();
        BaseExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(DictConstant.BYTE_YES_NO_N);

        if (!ObjectUtils.isEmpty(conditionDTO)) {
            StorageFileConditionDTO storageFileConditionDTO = (StorageFileConditionDTO) conditionDTO;
            if (!ObjectUtils.isEmpty(storageFileConditionDTO.getId())) {
                criteria.andIdEqualTo(storageFileConditionDTO.getId());
            }
            if (!CollectionUtils.isEmpty(storageFileConditionDTO.getIds())) {
                criteria.andIdIn(storageFileConditionDTO.getIds());
            }
        }
        return example;
    }

    @Transactional(readOnly = true)
    public Optional<StorageFileDTO> getByUuid(String uuid) {
        return Optional.ofNullable(mapStruct.fromEntity2DTO(mapper.selectByUuid(uuid)));
    }

}

package com.vinesmario.microservice.server.storage.service;

import com.vinesmario.common.constant.DictConstant;
import com.vinesmario.microservice.client.common.dto.condition.ConditionDTO;
import com.vinesmario.microservice.client.storage.dto.StorageImageDTO;
import com.vinesmario.microservice.client.storage.dto.condition.StorageImageConditionDTO;
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
public class StorageImageService extends BaseService<StorageImageDTO, StorageImage, Long> {

    private final StorageImageMapper mapper;
    private final StorageImageMapStruct mapStruct;

    public StorageImageService(StorageImageMapper mapper,
                               @Qualifier("storageImageMapStructImpl") StorageImageMapStruct mapStruct) {
        super(mapper, mapStruct);
        this.mapper = mapper;
        this.mapStruct = mapStruct;
    }

    public BaseExample fromConditionDTO2Example(ConditionDTO conditionDTO) {
        BaseExample example = new BaseExample();
        BaseExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(DictConstant.BYTE_YES_NO_N);

        if (!ObjectUtils.isEmpty(conditionDTO)) {
            StorageImageConditionDTO storageImageConditionDTO = (StorageImageConditionDTO) conditionDTO;
            if (!ObjectUtils.isEmpty(storageImageConditionDTO.getId())) {
                criteria.andIdEqualTo(storageImageConditionDTO.getId());
            }
            if (!CollectionUtils.isEmpty(storageImageConditionDTO.getIds())) {
                criteria.andIdIn(storageImageConditionDTO.getIds());
            }
        }
        return example;
    }

    @Transactional(readOnly = true)
    public Optional<StorageImageDTO> getByUuid(String uuid) {
        return Optional.ofNullable(mapStruct.fromEntity2DTO(mapper.selectByUuid(uuid)));
    }

}

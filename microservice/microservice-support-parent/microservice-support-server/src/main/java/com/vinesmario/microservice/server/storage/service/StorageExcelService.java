package com.vinesmario.microservice.server.storage.service;

import com.vinesmario.common.constant.DictConstant;
import com.vinesmario.microservice.client.common.dto.condition.ConditionDTO;
import com.vinesmario.microservice.client.storage.dto.condition.StorageExcelConditionDTO;
import com.vinesmario.microservice.client.storage.dto.StorageExcelDTO;
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
public class StorageExcelService extends BaseService<StorageExcelDTO, StorageExcel, Long> {

    private final StorageExcelMapper mapper;
    private final StorageExcelMapStruct mapStruct;

    public StorageExcelService(StorageExcelMapper mapper,
                               @Qualifier("storageExcelMapStructImpl") StorageExcelMapStruct mapStruct) {
        super(mapper, mapStruct);
        this.mapper = mapper;
        this.mapStruct = mapStruct;
    }

    public BaseExample fromConditionDTO2Example(ConditionDTO conditionDTO) {
        BaseExample example = new BaseExample();
        BaseExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(DictConstant.BYTE_YES_NO_N);

        if (!ObjectUtils.isEmpty(conditionDTO)) {
            StorageExcelConditionDTO storageExcelConditionDTO = (StorageExcelConditionDTO) conditionDTO;
            if (!ObjectUtils.isEmpty(storageExcelConditionDTO.getId())) {
                criteria.andIdEqualTo(storageExcelConditionDTO.getId());
            }
            if (!CollectionUtils.isEmpty(storageExcelConditionDTO.getIds())) {
                criteria.andIdIn(storageExcelConditionDTO.getIds());
            }
        }
        return example;
    }

    @Transactional(readOnly = true)
    public Optional<StorageExcelDTO> getByUuid(String uuid) {
        return Optional.ofNullable(mapStruct.fromEntity2DTO(mapper.selectByUuid(uuid)));
    }

}

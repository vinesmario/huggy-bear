package com.vinesmario.microservice.server.storage.service;

import com.vinesmario.common.constant.DictConstant;
import com.vinesmario.microservice.client.common.dto.condition.ConditionDTO;
import com.vinesmario.microservice.client.storage.dto.condition.StoragePdfConditionDTO;
import com.vinesmario.microservice.client.storage.dto.StoragePdfDTO;
import com.vinesmario.microservice.server.common.persistence.mybatis.BaseExample;
import com.vinesmario.microservice.server.common.service.mybatis.impl.BaseService;
import com.vinesmario.microservice.server.storage.po.StoragePdf;
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
public class StoragePdfService extends BaseService<StoragePdfDTO, StoragePdf, Long> {

    private final StoragePdfMapper mapper;
    private final StoragePdfMapStruct mapStruct;

    public StoragePdfService(StoragePdfMapper mapper,
                             @Qualifier("storagePdfMapStructImpl") StoragePdfMapStruct mapStruct) {
        super(mapper, mapStruct);
        this.mapper = mapper;
        this.mapStruct = mapStruct;
    }

    public BaseExample fromConditionDTO2Example(ConditionDTO conditionDTO) {
        BaseExample example = new BaseExample();
        BaseExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(DictConstant.BYTE_YES_NO_N);

        if (!ObjectUtils.isEmpty(conditionDTO)) {
            StoragePdfConditionDTO storagePdfConditionDTO = (StoragePdfConditionDTO) conditionDTO;
            if (!ObjectUtils.isEmpty(storagePdfConditionDTO.getId())) {
                criteria.andIdEqualTo(storagePdfConditionDTO.getId());
            }
            if (!CollectionUtils.isEmpty(storagePdfConditionDTO.getIds())) {
                criteria.andIdIn(storagePdfConditionDTO.getIds());
            }
        }
        return example;
    }

    @Transactional(readOnly = true)
    public Optional<StoragePdfDTO> getByUuid(String uuid) {
        return Optional.ofNullable(mapStruct.fromPO2DTO(mapper.selectByUuid(uuid)));
    }

}

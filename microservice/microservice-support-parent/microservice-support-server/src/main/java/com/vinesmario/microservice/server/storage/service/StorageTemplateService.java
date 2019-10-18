package com.vinesmario.microservice.server.storage.service;

import com.vinesmario.common.constant.DictConstant;
import com.vinesmario.microservice.client.common.dto.condition.ConditionDTO;
import com.vinesmario.microservice.client.storage.dto.StoragePdfSplitedDTO;
import com.vinesmario.microservice.client.storage.dto.condition.StorageTemplateConditionDTO;
import com.vinesmario.microservice.client.storage.dto.StorageTemplateDTO;
import com.vinesmario.microservice.server.common.persistence.mybatis.BaseExample;
import com.vinesmario.microservice.server.common.service.mybatis.impl.BaseService;
import com.vinesmario.microservice.server.storage.po.StorageTemplate;
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
public class StorageTemplateService extends BaseService<StorageTemplateDTO, StorageTemplate, Long> {

    private final StorageTemplateMapper mapper;
    private final StorageTemplateMapStruct mapStruct;

    public StorageTemplateService(StorageTemplateMapper mapper,
                                  @Qualifier("storageTemplateMapStructImpl") StorageTemplateMapStruct mapStruct) {
        super(mapper, mapStruct);
        this.mapper = mapper;
        this.mapStruct = mapStruct;
    }

    public BaseExample fromConditionDTO2Example(ConditionDTO conditionDTO) {
        BaseExample example = new BaseExample();
        BaseExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(DictConstant.BYTE_YES_NO_N);

        if (!ObjectUtils.isEmpty(conditionDTO)) {
            StorageTemplateConditionDTO storageTemplateConditionDTO = (StorageTemplateConditionDTO) conditionDTO;
            if (!ObjectUtils.isEmpty(storageTemplateConditionDTO.getId())) {
                criteria.andIdEqualTo(storageTemplateConditionDTO.getId());
            }
            if (!CollectionUtils.isEmpty(storageTemplateConditionDTO.getIds())) {
                criteria.andIdIn(storageTemplateConditionDTO.getIds());
            }
        }
        return example;
    }

    @Transactional(readOnly = true)
    public Optional<StorageTemplateDTO> getByUuid(String uuid) {
        return Optional.ofNullable(mapStruct.fromPO2DTO(mapper.selectByUuid(uuid)));
    }

}

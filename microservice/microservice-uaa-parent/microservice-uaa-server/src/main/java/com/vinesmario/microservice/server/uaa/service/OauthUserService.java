package com.vinesmario.microservice.server.uaa.service;

import com.vinesmario.common.constant.DictConstant;
import com.vinesmario.microservice.client.common.dto.condition.ConditionDTO;
import com.vinesmario.microservice.client.uaa.dto.OauthUserDTO;
import com.vinesmario.microservice.client.uaa.dto.condition.OauthUserConditionDTO;
import com.vinesmario.microservice.server.common.persistence.mybatis.BaseExample;
import com.vinesmario.microservice.server.common.service.mybatis.impl.BaseService;
import com.vinesmario.microservice.server.uaa.entity.OauthUser;
import com.vinesmario.microservice.server.uaa.mapper.OauthUserMapper;
import com.vinesmario.microservice.server.uaa.mapstruct.OauthUserMapStruct;
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
@Service
public class OauthUserService extends BaseService<OauthUserDTO, OauthUser, Long> {

    private final OauthUserMapper mapper;
    private final OauthUserMapStruct mapStruct;

    public OauthUserService(OauthUserMapper mapper,
                              @Qualifier("oauthUserMapStructImpl") OauthUserMapStruct mapStruct) {
        super(mapper, mapStruct);
        this.mapper = mapper;
        this.mapStruct = mapStruct;
    }

    public BaseExample fromConditionDTO2Example(ConditionDTO conditionDTO) {
        BaseExample example = new BaseExample();
        BaseExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(DictConstant.BYTE_YES_NO_N);

        if (!ObjectUtils.isEmpty(conditionDTO)) {
            OauthUserConditionDTO oauthUserConditionDTO = (OauthUserConditionDTO) conditionDTO;
            if (!ObjectUtils.isEmpty(oauthUserConditionDTO.getId())) {
                criteria.andIdEqualTo(oauthUserConditionDTO.getId());
            }
            if (!CollectionUtils.isEmpty(oauthUserConditionDTO.getIds())) {
                criteria.andIdIn(oauthUserConditionDTO.getIds());
            }
        }
        return example;
    }

    @Transactional(readOnly = true)
    public Optional<OauthUserDTO> getByUsername(String username) {
        return Optional.ofNullable(this.mapStruct.fromEntity2DTO(this.mapper.selectByUsername(username)));
    }

    @Transactional(readOnly = true)
    public Optional<OauthUserDTO> getByMobile(String mobile) {
        return Optional.ofNullable(this.mapStruct.fromEntity2DTO(this.mapper.selectByMobile(mobile)));
    }

    @Transactional(readOnly = true)
    public Optional<OauthUserDTO> getByEmail(String email) {
        return Optional.ofNullable(this.mapStruct.fromEntity2DTO(this.mapper.selectByEmail(email)));
    }

    @Transactional(readOnly = true)
    public Optional<OauthUserDTO> getWithAuthoritiesByUsername(String username) {
        Optional<OauthUserDTO> optional = Optional.ofNullable(this.mapStruct.fromEntity2DTO(this.mapper.selectByUsername(username)));
        // TODO 角色
        return optional;
    }

    @Transactional(readOnly = true)
    public Optional<OauthUserDTO> getWithAuthoritiesByMobile(String mobile) {
        return Optional.ofNullable(this.mapStruct.fromEntity2DTO(this.mapper.selectByMobile(mobile)));
    }

    @Transactional(readOnly = true)
    public Optional<OauthUserDTO> getWithAuthoritiesByEmail(String email) {
        return Optional.ofNullable(this.mapStruct.fromEntity2DTO(this.mapper.selectByEmail(email)));
    }

    public void create(OauthUserDTO dto) {
        OauthUser entity = mapStruct.fromDTO2Entity(dto);
        this.mapper.insert(entity);
        dto.setId(entity.getId());
        // 初始化用户角色
    }
}

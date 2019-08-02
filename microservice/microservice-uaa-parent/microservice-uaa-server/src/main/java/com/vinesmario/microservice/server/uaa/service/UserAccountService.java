package com.vinesmario.microservice.server.uaa.service;

import com.vinesmario.common.constant.DictConstant;
import com.vinesmario.microservice.client.common.dto.condition.ConditionDTO;
import com.vinesmario.microservice.client.uaa.dto.UserAccountDTO;
import com.vinesmario.microservice.client.uaa.dto.condition.UserAccountConditionDTO;
import com.vinesmario.microservice.server.common.persistence.mybatis.BaseExample;
import com.vinesmario.microservice.server.common.service.mybatis.impl.BaseService;
import com.vinesmario.microservice.server.uaa.entity.UserAccount;
import com.vinesmario.microservice.server.uaa.mapper.UserAccountMapper;
import com.vinesmario.microservice.server.uaa.mapstruct.UserAccountMapStruct;
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
public class UserAccountService extends BaseService<UserAccountDTO, UserAccount, Long> {

    private final UserAccountMapper mapper;
    private final UserAccountMapStruct mapStruct;

    public UserAccountService(UserAccountMapper mapper,
                              @Qualifier("userAccountMapStructImpl") UserAccountMapStruct mapStruct) {
        super(mapper, mapStruct);
        this.mapper = mapper;
        this.mapStruct = mapStruct;
    }

    public BaseExample fromConditionDTO2Example(ConditionDTO conditionDTO) {
        BaseExample example = new BaseExample();
        BaseExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(DictConstant.BYTE_YES_NO_N);

        if (!ObjectUtils.isEmpty(conditionDTO)) {
            UserAccountConditionDTO userAccountConditionDTO = (UserAccountConditionDTO) conditionDTO;
            if (!ObjectUtils.isEmpty(userAccountConditionDTO.getId())) {
                criteria.andIdEqualTo(userAccountConditionDTO.getId());
            }
            if (!CollectionUtils.isEmpty(userAccountConditionDTO.getIds())) {
                criteria.andIdIn(userAccountConditionDTO.getIds());
            }
        }
        return example;
    }

    @Transactional(readOnly = true)
    public Optional<UserAccountDTO> getByUsername(String username) {
        return Optional.ofNullable(this.mapStruct.fromEntity2DTO(this.mapper.selectByUsername(username)));
    }

    @Transactional(readOnly = true)
    public Optional<UserAccountDTO> getByMobile(String mobile) {
        return Optional.ofNullable(this.mapStruct.fromEntity2DTO(this.mapper.selectByMobile(mobile)));
    }

    @Transactional(readOnly = true)
    public Optional<UserAccountDTO> getByEmail(String email) {
        return Optional.ofNullable(this.mapStruct.fromEntity2DTO(this.mapper.selectByEmail(email)));
    }

    @Transactional(readOnly = true)
    public Optional<UserAccountDTO> getWithAuthoritiesByUsername(String username) {
        return Optional.ofNullable(this.mapStruct.fromEntity2DTO(this.mapper.selectByUsername(username)));
    }

    @Transactional(readOnly = true)
    public Optional<UserAccountDTO> getWithAuthoritiesByMobile(String mobile) {
        return Optional.ofNullable(this.mapStruct.fromEntity2DTO(this.mapper.selectByMobile(mobile)));
    }

    @Transactional(readOnly = true)
    public Optional<UserAccountDTO> getWithAuthoritiesByEmail(String email) {
        return Optional.ofNullable(this.mapStruct.fromEntity2DTO(this.mapper.selectByEmail(email)));
    }

    public void create(UserAccountDTO dto) {
        UserAccount entity = mapStruct.fromDTO2Entity(dto);
        this.mapper.insert(entity);
        dto.setId(entity.getId());
        // 初始化用户角色
    }
}

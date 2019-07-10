package com.vinesmario.microservice.server.uaa.service;

import com.vinesmario.microservice.client.common.dto.condition.ConditionDto;
import com.vinesmario.microservice.client.uaa.dto.UserAccountDto;
import com.vinesmario.microservice.client.uaa.dto.condition.UserAccountConditionDto;
import com.vinesmario.microservice.server.common.constant.DictConstant;
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
public class UserAccountService extends BaseService<UserAccountDto, UserAccount, Long> {

    private final UserAccountMapper mapper;
    private final UserAccountMapStruct mapStruct;

    public UserAccountService(UserAccountMapper mapper,
                              @Qualifier("userAccountMapStructImpl") UserAccountMapStruct mapStruct) {
        super(mapper, mapStruct);
        this.mapper = mapper;
        this.mapStruct = mapStruct;
    }

    public BaseExample fromConditionDto2Example(ConditionDto conditionDto) {
        BaseExample example = new BaseExample();
        BaseExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(DictConstant.BYTE_YES_NO_N);

        if (!ObjectUtils.isEmpty(conditionDto)) {
            UserAccountConditionDto userAccountConditionDto = (UserAccountConditionDto) conditionDto;
            if (!ObjectUtils.isEmpty(userAccountConditionDto.getId())) {
                criteria.andIdEqualTo(userAccountConditionDto.getId());
            }
            if (!CollectionUtils.isEmpty(userAccountConditionDto.getIds())) {
                criteria.andIdIn(userAccountConditionDto.getIds());
            }
        }
        return example;
    }

    @Transactional(readOnly = true)
    public Optional<UserAccountDto> getByUsername(String username) {
        return Optional.ofNullable(this.mapStruct.fromEntity2Dto(this.mapper.selectByUsername(username)));
    }

    @Transactional(readOnly = true)
    public Optional<UserAccountDto> getByMobile(String mobile) {
        return Optional.ofNullable(this.mapStruct.fromEntity2Dto(this.mapper.selectByMobile(mobile)));
    }

    @Transactional(readOnly = true)
    public Optional<UserAccountDto> getByEmail(String email) {
        return Optional.ofNullable(this.mapStruct.fromEntity2Dto(this.mapper.selectByEmail(email)));
    }

    @Transactional(readOnly = true)
    public Optional<UserAccountDto> getWithAuthoritiesByUsername(String username) {
        return Optional.ofNullable(this.mapStruct.fromEntity2Dto(this.mapper.selectByUsername(username)));
    }

    @Transactional(readOnly = true)
    public Optional<UserAccountDto> getWithAuthoritiesByMobile(String mobile) {
        return Optional.ofNullable(this.mapStruct.fromEntity2Dto(this.mapper.selectByMobile(mobile)));
    }

    @Transactional(readOnly = true)
    public Optional<UserAccountDto> getWithAuthoritiesByEmail(String email) {
        return Optional.ofNullable(this.mapStruct.fromEntity2Dto(this.mapper.selectByEmail(email)));
    }

    public void create(UserAccountDto dto) {
        UserAccount entity = mapStruct.fromDto2Entity(dto);
        this.mapper.insert(entity);
        dto.setId(entity.getId());
        // 初始化用户角色
    }
}

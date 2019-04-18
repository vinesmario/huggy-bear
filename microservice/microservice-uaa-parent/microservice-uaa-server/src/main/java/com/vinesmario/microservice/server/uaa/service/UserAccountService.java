package com.vinesmario.microservice.server.uaa.service;

import com.vinesmario.microservice.client.common.dto.condition.ConditionDto;
import com.vinesmario.microservice.client.uaa.dto.UserAccountDto;
import com.vinesmario.microservice.client.uaa.dto.condition.UserAccountConditionDto;
import com.vinesmario.microservice.server.common.persistence.mybatis.BaseExample;
import com.vinesmario.microservice.server.common.service.mybatis.BaseService;
import com.vinesmario.microservice.server.uaa.entity.UserAccount;
import com.vinesmario.microservice.server.uaa.mapper.UserAccountMapper;
import com.vinesmario.microservice.server.uaa.mapstruct.UserAccountMapStructImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

/**
 * @author maodipu
 * @date 2018-01-18
 */
@Data
@Service
public class UserAccountService extends BaseService<UserAccountDto, UserAccount, Long> {

    @Autowired
    private UserAccountMapper mapper;
    @Autowired
    private UserAccountMapStructImpl mapStruct;

    public BaseExample fromConditionDto2Example(ConditionDto conditionDto) {
        BaseExample example = new BaseExample();
        BaseExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(YES_NO_N);

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
        return Optional.ofNullable(getMapStruct().fromEntity2Dto(getMapper().selectByUsername(username)));
    }

    @Transactional(readOnly = true)
    public Optional<UserAccountDto> getByMobile(String mobile) {
        return Optional.ofNullable(getMapStruct().fromEntity2Dto(getMapper().selectByMobile(mobile)));
    }

    @Transactional(readOnly = true)
    public Optional<UserAccountDto> getByEmail(String email) {
        return Optional.ofNullable(getMapStruct().fromEntity2Dto(getMapper().selectByEmail(email)));
    }

    @Transactional(readOnly = true)
    public Optional<UserAccountDto> getWithAuthoritiesByUsername(String username) {
        return Optional.ofNullable(getMapStruct().fromEntity2Dto(getMapper().selectByUsername(username)));
    }

    @Transactional(readOnly = true)
    public Optional<UserAccountDto> getWithAuthoritiesByMobile(String mobile) {
        return Optional.ofNullable(getMapStruct().fromEntity2Dto(getMapper().selectByMobile(mobile)));
    }

    @Transactional(readOnly = true)
    public Optional<UserAccountDto> getWithAuthoritiesByEmail(String email) {
        return Optional.ofNullable(getMapStruct().fromEntity2Dto(getMapper().selectByEmail(email)));
    }
}

package com.vinesmario.microservice.server.common.service.mybatis.impl;

import com.vinesmario.microservice.client.common.dto.BaseDto;
import com.vinesmario.microservice.client.common.dto.condition.ConditionDto;
import com.vinesmario.microservice.server.common.constant.DictConstant;
import com.vinesmario.microservice.server.common.entity.BaseEntity;
import com.vinesmario.microservice.server.common.mapstruct.BaseMapStruct;
import com.vinesmario.microservice.server.common.persistence.mybatis.BaseExample;
import com.vinesmario.microservice.server.common.persistence.mybatis.mapper.CrudMapper;
import com.vinesmario.microservice.server.common.service.mybatis.CrudService;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Transactional
public abstract class BaseService<DTO extends BaseDto, T extends BaseEntity<PK>, PK extends Serializable>
        extends SimpleService<DTO, T, PK>
        implements CrudService<DTO, PK>
{

    private final CrudMapper<T, PK> mapper;

    private final BaseMapStruct<T, DTO> mapStruct;

    public BaseService(CrudMapper<T, PK> mapper, BaseMapStruct<T, DTO> mapStruct) {
        super(mapper, mapStruct);
        this.mapper = mapper;
        this.mapStruct = mapStruct;
    }

    public abstract BaseExample fromConditionDto2Example(ConditionDto conditionDto);

    public void create(DTO dto) {
        T entity = mapStruct.fromDto2Entity(dto);
        this.mapper.insert(entity);
        dto.setId(entity.getId());
    }

    /**
     * 逻辑删除
     *
     * @param dto
     */
    public void delete(DTO dto) {
        T entity = mapStruct.fromDto2Entity(dto);
        entity.setDeleted(DictConstant.BYTE_YES_NO_Y);
        this.mapper.updateByPrimaryKeySelective(entity);
    }

    public void modify(DTO dto) {
        T entity = mapStruct.fromDto2Entity(dto);
        this.mapper.updateByPrimaryKey(entity);
    }

    public void insert(DTO dto) {
        T entity = mapStruct.fromDto2Entity(dto);
        this.mapper.insert(entity);
    }

    public void insertSelective(DTO dto) {
        T entity = mapStruct.fromDto2Entity(dto);
        this.mapper.insertSelective(entity);
    }

    public void deleteByPrimaryKey(PK primaryKey) {
        this.mapper.deleteByPrimaryKey(primaryKey);
    }

    public void deleteByExample(ConditionDto conditionDto) {
        BaseExample example = fromConditionDto2Example(conditionDto);
        this.mapper.deleteByExample(example);
    }

    public void updateByPrimaryKey(DTO dto) {
        T entity = mapStruct.fromDto2Entity(dto);
        this.mapper.updateByPrimaryKey(entity);
    }

    public void updateByPrimaryKeySelective(DTO dto) {
        T entity = mapStruct.fromDto2Entity(dto);
        this.mapper.updateByPrimaryKeySelective(entity);
    }

    public void updateByExample(DTO dto, ConditionDto conditionDto) {
        T entity = mapStruct.fromDto2Entity(dto);
        BaseExample example = fromConditionDto2Example(conditionDto);
        this.mapper.updateByExample(entity, example);
    }

    public void updateByExampleSelective(DTO dto, ConditionDto conditionDto) {
        T entity = mapStruct.fromDto2Entity(dto);
        BaseExample example = fromConditionDto2Example(conditionDto);
        this.mapper.updateByExampleSelective(entity, example);
    }

}

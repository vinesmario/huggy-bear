package com.vinesmario.microservice.server.common.service.mybatis.impl;

import com.vinesmario.common.constant.DictConstant;
import com.vinesmario.microservice.client.common.dto.BaseDto;
import com.vinesmario.microservice.client.common.dto.condition.ConditionDto;
import com.vinesmario.microservice.server.common.entity.BaseEntity;
import com.vinesmario.microservice.server.common.mapstruct.BaseMapStruct;
import com.vinesmario.microservice.server.common.persistence.mybatis.BaseExample;
import com.vinesmario.microservice.server.common.persistence.mybatis.mapper.CrudMapper;
import com.vinesmario.microservice.server.common.service.mybatis.CrudService;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;

@Transactional
public abstract class BaseService<DTO extends BaseDto, T extends BaseEntity<PK>, PK extends Serializable>
        extends SimpleService<DTO, T, PK>
        implements CrudService<DTO, PK> {

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
        dto.setDeleted(DictConstant.BYTE_YES_NO_N);
        mapper.insert(entity);
        dto.setId(entity.getId());
    }

    /**
     * 逻辑删除
     *
     * @param dto
     */
    public void remove(DTO dto) {
        T entity = mapStruct.fromDto2Entity(dto);
        entity.setDeleted(DictConstant.BYTE_YES_NO_Y);
        mapper.updateByPrimaryKeySelective(entity);
    }

    public void remove(ConditionDto conditionDto) {
        BaseExample baseExample = fromConditionDto2Example(conditionDto);
        List<T> dbEntityList = mapper.selectByExample(baseExample);
        if (!CollectionUtils.isEmpty(dbEntityList)) {
            for (T dbEntity : dbEntityList) {
                dbEntity.setDeleted(DictConstant.BYTE_YES_NO_Y);
                mapper.updateByPrimaryKeySelective(dbEntity);
            }
        }
    }

    public void modify(DTO dto) {
        T entity = mapStruct.fromDto2Entity(dto);
        mapper.updateByPrimaryKey(entity);
    }

    public void modify(DTO dto, ConditionDto conditionDto) {
        BaseExample baseExample = fromConditionDto2Example(conditionDto);
        List<T> dbEntityList = mapper.selectByExample(baseExample);
        if (!CollectionUtils.isEmpty(dbEntityList)) {
            T entity = mapStruct.fromDto2Entity(dto);
            for (T dbEntity : dbEntityList) {
                BeanUtils.copyProperties(entity, dbEntity, "id", "createBy", "createDate");
                mapper.updateByPrimaryKey(dbEntity);
            }
        }
    }

    @Deprecated
    public void insert(DTO dto) {
        T entity = mapStruct.fromDto2Entity(dto);
        entity.setDeleted(DictConstant.BYTE_YES_NO_N);
        mapper.insert(entity);
        dto.setId(entity.getId());
    }

    @Deprecated
    public void insertSelective(DTO dto) {
        T entity = mapStruct.fromDto2Entity(dto);
        entity.setDeleted(DictConstant.BYTE_YES_NO_N);
        mapper.insertSelective(entity);
        dto.setId(entity.getId());
    }

    @Deprecated
    public void deleteByPrimaryKey(PK primaryKey) {
        mapper.deleteByPrimaryKey(primaryKey);
    }

    @Deprecated
    public void deleteByExample(ConditionDto conditionDto) {
        BaseExample example = fromConditionDto2Example(conditionDto);
        mapper.deleteByExample(example);
    }

    @Deprecated
    public void updateByPrimaryKey(DTO dto) {
        T entity = mapStruct.fromDto2Entity(dto);
        mapper.updateByPrimaryKey(entity);
    }

    @Deprecated
    public void updateByPrimaryKeySelective(DTO dto) {
        T entity = mapStruct.fromDto2Entity(dto);
        mapper.updateByPrimaryKeySelective(entity);
    }

    @Deprecated
    public void updateByExample(DTO dto, ConditionDto conditionDto) {
        T entity = mapStruct.fromDto2Entity(dto);
        BaseExample example = fromConditionDto2Example(conditionDto);
        mapper.updateByExample(entity, example);
    }

    @Deprecated
    public void updateByExampleSelective(DTO dto, ConditionDto conditionDto) {
        T entity = mapStruct.fromDto2Entity(dto);
        BaseExample example = fromConditionDto2Example(conditionDto);
        mapper.updateByExampleSelective(entity, example);
    }

}

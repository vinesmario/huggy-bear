package com.vinesmario.microservice.server.common.service.mybatis;

import com.vinesmario.microservice.client.common.dto.BaseDto;
import com.vinesmario.microservice.client.common.dto.condition.ConditionDto;
import com.vinesmario.microservice.server.common.constant.DictConstant;
import com.vinesmario.microservice.server.common.entity.BaseEntity;
import com.vinesmario.microservice.server.common.mapstruct.BaseMapStruct;
import com.vinesmario.microservice.server.common.persistence.mybatis.BaseExample;
import com.vinesmario.microservice.server.common.persistence.mybatis.mapper.CrudMapper;
import com.vinesmario.microservice.server.common.service.CrudService;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;

@Transactional
public abstract class BaseService<DTO extends BaseDto, T extends BaseEntity<PK>, PK extends Serializable>
        extends SimpleService<DTO, T, PK>
        implements CrudService<DTO, PK> {

    public final static byte YES_NO_Y = 1;
    public final static byte YES_NO_N = 0;

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
        entity.setCreatedDate(LocalDateTime.now())
                .setLastModifiedDate(LocalDateTime.now())
                .setDeleted(DictConstant.BYTE_YES_NO_N);
        this.mapper.insert(entity);
        dto.setId(entity.getId());
    }

    public void delete(PK primaryKey) {
        this.mapper.deleteByPrimaryKey(primaryKey);
    }

    public void modify(DTO dto) {
        T entity = mapStruct.fromDto2Entity(dto);
        entity.setLastModifiedDate(LocalDateTime.now());
        this.mapper.updateByPrimaryKey(entity);
    }

    public void insertSelective(DTO dto) {
        T entity = mapStruct.fromDto2Entity(dto);
        entity.setCreatedDate(LocalDateTime.now())
                .setLastModifiedDate(LocalDateTime.now());
        this.mapper.insertSelective(entity);
    }

    public void deleteByExample(ConditionDto conditionDto) {
        BaseExample example = fromConditionDto2Example(conditionDto);
        this.mapper.deleteByExample(example);
    }

    public void updateByPrimaryKeySelective(DTO dto) {
        T entity = mapStruct.fromDto2Entity(dto);
        entity.setLastModifiedDate(LocalDateTime.now());
        this.mapper.updateByPrimaryKeySelective(entity);
    }

    public void updateByExample(DTO dto, ConditionDto conditionDto) {
        BaseExample example = fromConditionDto2Example(conditionDto);
        T entity = mapStruct.fromDto2Entity(dto);
        entity.setLastModifiedDate(LocalDateTime.now());
        this.mapper.updateByExample(entity, example);
    }

    public void updateByExampleSelective(DTO dto, ConditionDto conditionDto) {
        BaseExample example = fromConditionDto2Example(conditionDto);
        T entity = mapStruct.fromDto2Entity(dto);
        entity.setLastModifiedDate(LocalDateTime.now());
        this.mapper.updateByExampleSelective(entity, example);
    }

}

package com.vinesmario.microservice.server.common.service.mybatis;

import com.vinesmario.microservice.client.common.dto.BaseDto;
import com.vinesmario.microservice.client.common.dto.condition.ConditionDto;
import com.vinesmario.microservice.server.common.constant.DictConstant;
import com.vinesmario.microservice.server.common.entity.BaseEntity;
import com.vinesmario.microservice.server.common.mapstruct.BaseMapStruct;
import com.vinesmario.microservice.server.common.persistence.mybatis.BaseExample;
import com.vinesmario.microservice.server.common.persistence.mybatis.mapper.CrudMapper;
import com.vinesmario.microservice.server.common.service.CrudService;
import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Transactional
public abstract class BaseService<DTO extends BaseDto, T extends BaseEntity<PK>, PK extends Serializable>
        extends SimpleService<DTO, T, PK>
        implements CrudService<DTO, PK> {

    public final static byte YES_NO_Y = 1;
    public final static byte YES_NO_N = 0;

    private CrudMapper<T, PK> mapper;

    private BaseMapStruct<T, DTO> mapStruct;

    public abstract BaseExample fromConditionDto2Example(ConditionDto conditionDto);

    public void create(DTO dto) {
        T entity = getMapStruct().fromDto2Entity(dto);
        entity.setCreatedDate(LocalDateTime.now())
                .setLastModifiedDate(LocalDateTime.now())
                .setDeleted(DictConstant.BYTE_YES_NO_N);
        getMapper().insert(entity);
        dto.setId(entity.getId());
    }

    public void delete(PK primaryKey) {
        getMapper().deleteByPrimaryKey(primaryKey);
    }

    public void modify(DTO dto) {
        T entity = getMapStruct().fromDto2Entity(dto);
        entity.setLastModifiedDate(LocalDateTime.now());
        getMapper().updateByPrimaryKey(entity);
    }

    public void insertSelective(DTO dto) {
        T entity = getMapStruct().fromDto2Entity(dto);
        entity.setCreatedDate(LocalDateTime.now())
                .setLastModifiedDate(LocalDateTime.now());
        getMapper().insertSelective(entity);
    }

    public void deleteByExample(ConditionDto conditionDto) {
        BaseExample example = fromConditionDto2Example(conditionDto);
        getMapper().deleteByExample(example);
    }

    public void updateByPrimaryKeySelective(DTO dto) {
        T entity = getMapStruct().fromDto2Entity(dto);
        entity.setLastModifiedDate(LocalDateTime.now());
        getMapper().updateByPrimaryKeySelective(entity);
    }

    public void updateByExample(DTO dto, ConditionDto conditionDto) {
        BaseExample example = fromConditionDto2Example(conditionDto);
        T entity = getMapStruct().fromDto2Entity(dto);
        entity.setLastModifiedDate(LocalDateTime.now());
        getMapper().updateByExample(entity, example);
    }

    public void updateByExampleSelective(DTO dto, ConditionDto conditionDto) {
        BaseExample example = fromConditionDto2Example(conditionDto);
        T entity = getMapStruct().fromDto2Entity(dto);
        entity.setLastModifiedDate(LocalDateTime.now());
        getMapper().updateByExampleSelective(entity, example);
    }

}

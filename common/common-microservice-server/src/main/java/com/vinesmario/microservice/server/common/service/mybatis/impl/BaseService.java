package com.vinesmario.microservice.server.common.service.mybatis.impl;

import com.vinesmario.common.constant.DictConstant;
import com.vinesmario.microservice.client.common.dto.BaseDTO;
import com.vinesmario.microservice.client.common.dto.condition.ConditionDTO;
import com.vinesmario.microservice.server.common.po.BasePO;
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
public abstract class BaseService<DTO extends BaseDTO, T extends BasePO<PK>, PK extends Serializable>
        extends SimpleService<DTO, T, PK>
        implements CrudService<DTO, PK> {

    private final CrudMapper<T, PK> mapper;

    private final BaseMapStruct<T, DTO> mapStruct;

    public BaseService(CrudMapper<T, PK> mapper, BaseMapStruct<T, DTO> mapStruct) {
        super(mapper, mapStruct);
        this.mapper = mapper;
        this.mapStruct = mapStruct;
    }

    public abstract BaseExample fromConditionDTO2Example(ConditionDTO conditionDTO);

    public void create(DTO dto) {
        T entity = mapStruct.fromDTO2PO(dto);
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
        T entity = mapStruct.fromDTO2PO(dto);
        entity.setDeleted(DictConstant.BYTE_YES_NO_Y);
        mapper.updateByPrimaryKeySelective(entity);
    }

    public void remove(ConditionDTO conditionDTO) {
        BaseExample baseExample = fromConditionDTO2Example(conditionDTO);
        List<T> dbEntityList = mapper.selectByExample(baseExample);
        if (!CollectionUtils.isEmpty(dbEntityList)) {
            for (T dbEntity : dbEntityList) {
                dbEntity.setDeleted(DictConstant.BYTE_YES_NO_Y);
                mapper.updateByPrimaryKeySelective(dbEntity);
            }
        }
    }

    public void modify(DTO dto) {
        T entity = mapStruct.fromDTO2PO(dto);
        mapper.updateByPrimaryKey(entity);
    }

    public void modify(DTO dto, ConditionDTO conditionDTO) {
        BaseExample baseExample = fromConditionDTO2Example(conditionDTO);
        List<T> dbEntityList = mapper.selectByExample(baseExample);
        if (!CollectionUtils.isEmpty(dbEntityList)) {
            T entity = mapStruct.fromDTO2PO(dto);
            for (T dbEntity : dbEntityList) {
                BeanUtils.copyProperties(entity, dbEntity, "id", "createBy", "createDate");
                mapper.updateByPrimaryKey(dbEntity);
            }
        }
    }

    @Deprecated
    public void insert(DTO dto) {
        T entity = mapStruct.fromDTO2PO(dto);
        entity.setDeleted(DictConstant.BYTE_YES_NO_N);
        mapper.insert(entity);
        dto.setId(entity.getId());
    }

    @Deprecated
    public void insertSelective(DTO dto) {
        T entity = mapStruct.fromDTO2PO(dto);
        entity.setDeleted(DictConstant.BYTE_YES_NO_N);
        mapper.insertSelective(entity);
        dto.setId(entity.getId());
    }

    @Deprecated
    public void deleteByPrimaryKey(PK primaryKey) {
        mapper.deleteByPrimaryKey(primaryKey);
    }

    @Deprecated
    public void deleteByExample(ConditionDTO conditionDTO) {
        BaseExample example = fromConditionDTO2Example(conditionDTO);
        mapper.deleteByExample(example);
    }

    @Deprecated
    public void updateByPrimaryKey(DTO dto) {
        T entity = mapStruct.fromDTO2PO(dto);
        mapper.updateByPrimaryKey(entity);
    }

    @Deprecated
    public void updateByPrimaryKeySelective(DTO dto) {
        T entity = mapStruct.fromDTO2PO(dto);
        mapper.updateByPrimaryKeySelective(entity);
    }

    @Deprecated
    public void updateByExample(DTO dto, ConditionDTO conditionDTO) {
        T entity = mapStruct.fromDTO2PO(dto);
        BaseExample example = fromConditionDTO2Example(conditionDTO);
        mapper.updateByExample(entity, example);
    }

    @Deprecated
    public void updateByExampleSelective(DTO dto, ConditionDTO conditionDTO) {
        T entity = mapStruct.fromDTO2PO(dto);
        BaseExample example = fromConditionDTO2Example(conditionDTO);
        mapper.updateByExampleSelective(entity, example);
    }

}

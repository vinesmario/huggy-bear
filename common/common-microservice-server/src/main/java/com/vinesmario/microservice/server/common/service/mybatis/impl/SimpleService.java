package com.vinesmario.microservice.server.common.service.mybatis.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.vinesmario.common.kit.StringKit;
import com.vinesmario.microservice.client.common.dto.BaseDTO;
import com.vinesmario.microservice.client.common.dto.condition.ConditionDTO;
import com.vinesmario.microservice.server.common.entity.BaseEntity;
import com.vinesmario.microservice.server.common.mapstruct.BaseMapStruct;
import com.vinesmario.microservice.server.common.persistence.mybatis.BaseExample;
import com.vinesmario.microservice.server.common.persistence.mybatis.mapper.ReadOnlyMapper;
import com.vinesmario.microservice.server.common.service.mybatis.ReadOnlyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Transactional
public abstract class SimpleService<DTO extends BaseDTO, T extends BaseEntity<PK>, PK extends Serializable>
        implements ReadOnlyService<DTO, PK> {

    private final ReadOnlyMapper<T, PK> mapper;

    private final BaseMapStruct<T, DTO> mapStruct;

    public SimpleService(ReadOnlyMapper<T, PK> mapper, BaseMapStruct<T, DTO> mapStruct) {
        this.mapper = mapper;
        this.mapStruct = mapStruct;
    }

    public abstract BaseExample fromConditionDTO2Example(ConditionDTO condition);

    @Transactional(readOnly = true)
    public Integer count(ConditionDTO conditionDTO) {
        BaseExample example = fromConditionDTO2Example(conditionDTO);
        return mapper.countByExample(example);
    }

    @Transactional(readOnly = true)
    public Page<DTO> page(ConditionDTO conditionDTO, Pageable pageable) {
        // 超过最大pageNum数，返回空。
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize(), true, false, false);
        BaseExample example = fromConditionDTO2Example(conditionDTO);

        if (!ObjectUtils.isEmpty(pageable.getSort())
                && pageable.getSort().isSorted()) {
            List<String> orderByClauseList = Lists.newArrayList();
            Iterator<Sort.Order> it = pageable.getSort().iterator();
            while (it.hasNext()) {
                Sort.Order order = it.next();
                if (!ObjectUtils.isEmpty(order)) {
                    if (order.getDirection().isAscending()) {
                        orderByClauseList.add(StringKit.camel2Underline(order.getProperty()) + " asc");
                    } else {
                        orderByClauseList.add(StringKit.camel2Underline(order.getProperty()) + " desc");
                    }
                }
            }
            if (!CollectionUtils.isEmpty(orderByClauseList)) {
                example.setOrderByClause(StringUtils.join(orderByClauseList.toArray(), ","));
            }
        }

        List<T> list = mapper.selectByExample(example);
        PageInfo<T> pageInfo = new PageInfo<>(list);

        // 转换为org.springframework.data.domain.PageImpl对象
        // 以便于在不修改前端和controller层的情况下能够快速切换到JPA
        return new PageImpl<>(mapStruct.fromEntities2DTOs(pageInfo.getList()), pageable, pageInfo.getTotal());
    }

    @Transactional(readOnly = true)
    public List<DTO> list(ConditionDTO conditionDTO) {
        return list(conditionDTO, null);
    }

    @Transactional(readOnly = true)
    public List<DTO> list(ConditionDTO conditionDTO, Sort sort) {
        BaseExample example = fromConditionDTO2Example(conditionDTO);

        if (!ObjectUtils.isEmpty(sort) && sort.isSorted()) {
            List<String> orderByClauseList = Lists.newArrayList();
            Iterator<Sort.Order> it = sort.iterator();
            while (it.hasNext()) {
                Sort.Order order = it.next();
                if (!ObjectUtils.isEmpty(order)) {
                    if (order.getDirection().isAscending()) {
                        orderByClauseList.add(StringKit.camel2Underline(order.getProperty()) + " asc");
                    } else {
                        orderByClauseList.add(StringKit.camel2Underline(order.getProperty()) + " desc");
                    }
                }
            }
            if (!CollectionUtils.isEmpty(orderByClauseList)) {
                example.setOrderByClause(StringUtils.join(orderByClauseList.toArray(), ","));
            }
        }

        return mapStruct.fromEntities2DTOs(mapper.selectByExample(example));
    }

    @Transactional(readOnly = true)
    public Optional<DTO> get(PK primaryKey) {
        return Optional.ofNullable(mapStruct.fromEntity2DTO(mapper.selectByPrimaryKey(primaryKey)));
    }

}

package com.vinesmario.microservice.server.common.service.mybatis;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.vinesmario.microservice.client.common.dto.BaseDto;
import com.vinesmario.microservice.client.common.dto.condition.ConditionDto;
import com.vinesmario.microservice.server.common.entity.BaseEntity;
import com.vinesmario.microservice.server.common.kit.StringKit;
import com.vinesmario.microservice.server.common.mapstruct.BaseMapStruct;
import com.vinesmario.microservice.server.common.persistence.mybatis.BaseExample;
import com.vinesmario.microservice.server.common.persistence.mybatis.mapper.RetrieveMapper;
import com.vinesmario.microservice.server.common.service.RetrieveService;
import lombok.Data;
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

@Data
@Transactional
public abstract class SimpleService<DTO extends BaseDto, T extends BaseEntity<PK>, PK extends Serializable>
        implements RetrieveService<DTO, PK> {

    private RetrieveMapper<T, PK> mapper;

    private BaseMapStruct<T, DTO> mapStruct;

    public abstract BaseExample fromConditionDto2Example(ConditionDto condition);

    @Transactional(readOnly = true)
    public Integer count(ConditionDto conditionDto) {
        BaseExample example = fromConditionDto2Example(conditionDto);
        return getMapper().countByExample(example);
    }

    @Transactional(readOnly = true)
    public Page<DTO> page(ConditionDto conditionDto, Pageable pageable) {
        // 超过最大pageNum数，返回空。
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize(), true, false, false);
        BaseExample example = fromConditionDto2Example(conditionDto);

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

        List<T> list = getMapper().selectByExample(example);
        PageInfo<T> pageInfo = new PageInfo<>(list);

        // 转换为org.springframework.data.domain.PageImpl对象
        // 以便于在不修改前端和controller层的情况下能够快速切换到JPA
        return new PageImpl<>(getMapStruct().fromEntities2Dtos(pageInfo.getList()), pageable, pageInfo.getTotal());
    }

    @Transactional(readOnly = true)
    public List<DTO> list(ConditionDto conditionDto) {
        return list(conditionDto, null);
    }

    @Transactional(readOnly = true)
    public List<DTO> list(ConditionDto conditionDto, Sort sort) {
        BaseExample example = fromConditionDto2Example(conditionDto);

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

        return getMapStruct().fromEntities2Dtos(getMapper().selectByExample(example));
    }

    @Transactional(readOnly = true)
    public Optional<DTO> get(PK primaryKey) {
        return Optional.ofNullable(getMapStruct().fromEntity2Dto(getMapper().selectByPrimaryKey(primaryKey)));
    }

}

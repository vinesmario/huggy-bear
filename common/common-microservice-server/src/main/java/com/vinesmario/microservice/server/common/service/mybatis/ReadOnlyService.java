package com.vinesmario.microservice.server.common.service.mybatis;

import com.vinesmario.microservice.client.common.dto.condition.ConditionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface ReadOnlyService<T, PK> {

    Integer count(ConditionDTO dto);

    Page<T> page(ConditionDTO conditionDTO, Pageable pageable);

    List<T> list(ConditionDTO conditionDTO, Sort sort);

    List<T> list(ConditionDTO conditionDTO);

    Optional<T> get(PK primaryKey);

}

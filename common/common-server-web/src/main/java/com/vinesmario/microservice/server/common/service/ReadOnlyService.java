package com.vinesmario.microservice.server.common.service;

import com.vinesmario.microservice.client.common.dto.condition.ConditionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface ReadOnlyService<T, PK> {

    Integer count(ConditionDto dto);

    Page<T> page(ConditionDto conditionDto, Pageable pageable);

    List<T> list(ConditionDto conditionDto, Sort sort);

    List<T> list(ConditionDto conditionDto);

    Optional<T> get(PK primaryKey);

}

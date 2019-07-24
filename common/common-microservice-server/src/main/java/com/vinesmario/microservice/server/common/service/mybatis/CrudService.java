package com.vinesmario.microservice.server.common.service.mybatis;

import com.vinesmario.microservice.client.common.dto.condition.ConditionDto;

public interface CrudService<T, PK> extends ReadOnlyService<T, PK> {

    void create(T t);

    void remove(T t);

    void remove(ConditionDto conditionDto);

    void modify(T t);

    void modify(T t, ConditionDto conditionDto);

}

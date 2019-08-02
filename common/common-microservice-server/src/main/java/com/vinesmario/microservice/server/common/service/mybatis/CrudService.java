package com.vinesmario.microservice.server.common.service.mybatis;

import com.vinesmario.microservice.client.common.dto.condition.ConditionDTO;

public interface CrudService<T, PK> extends ReadOnlyService<T, PK> {

    void create(T t);

    void remove(T t);

    void remove(ConditionDTO conditionDTO);

    void modify(T t);

    void modify(T t, ConditionDTO conditionDTO);

}

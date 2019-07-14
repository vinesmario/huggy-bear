package com.vinesmario.microservice.server.common.service.mybatis;

public interface CrudService<T, PK> extends ReadOnlyService<T, PK> {

    void create(T t);

    void delete(T t);

    void deleteIterable(Iterable<T> iterable);

    void modify(T t);
}

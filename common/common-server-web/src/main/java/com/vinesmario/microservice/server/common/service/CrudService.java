package com.vinesmario.microservice.server.common.service;

public interface CrudService<T, PK> extends RetrieveService<T, PK> {

    void create(T t);

    void delete(T t);

    void modify(T t);

}

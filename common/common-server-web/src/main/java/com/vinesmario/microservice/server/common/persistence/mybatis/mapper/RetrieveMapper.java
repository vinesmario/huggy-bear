package com.vinesmario.microservice.server.common.persistence.mybatis.mapper;

import com.vinesmario.microservice.server.common.entity.BaseEntity;
import com.vinesmario.microservice.server.common.persistence.mybatis.BaseExample;

import java.io.Serializable;
import java.util.List;

public interface RetrieveMapper<T extends BaseEntity, PK extends Serializable> {

    Integer countByExample(BaseExample example);

    List<T> selectByExample(BaseExample example);

    T selectByPrimaryKey(PK primaryKey);

}

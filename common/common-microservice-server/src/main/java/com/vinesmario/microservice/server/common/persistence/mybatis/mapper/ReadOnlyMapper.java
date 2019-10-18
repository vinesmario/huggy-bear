package com.vinesmario.microservice.server.common.persistence.mybatis.mapper;

import com.vinesmario.microservice.server.common.po.BasePO;
import com.vinesmario.microservice.server.common.persistence.mybatis.BaseExample;

import java.io.Serializable;
import java.util.List;

public interface ReadOnlyMapper<T extends BasePO, PK extends Serializable> {

    Integer countByExample(BaseExample example);

    List<T> selectByExample(BaseExample example);

    T selectByPrimaryKey(PK primaryKey);

}

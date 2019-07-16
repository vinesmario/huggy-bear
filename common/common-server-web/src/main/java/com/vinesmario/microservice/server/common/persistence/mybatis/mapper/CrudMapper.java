package com.vinesmario.microservice.server.common.persistence.mybatis.mapper;

import com.vinesmario.microservice.server.common.entity.BaseEntity;
import com.vinesmario.microservice.server.common.persistence.mybatis.BaseExample;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

public interface CrudMapper<T extends BaseEntity<PK>, PK extends Serializable>
        extends ReadOnlyMapper<T, PK> {

    /**
     * xml中需要添加  useGeneratedKeys="true" keyProperty="id"
     *
     * @param entity
     */
    void insert(T entity);

    /**
     * xml中需要添加 useGeneratedKeys="true" keyProperty="id"
     *
     * @param entity
     */
    void insertSelective(T entity);

    void deleteByPrimaryKey(PK primaryKey);

    @Deprecated
    void deleteByExample(BaseExample example);

    void updateByPrimaryKey(T entity);

    void updateByPrimaryKeySelective(T entity);

    @Deprecated
    void updateByExample(@Param("record") T record, @Param("example") BaseExample example);

    @Deprecated
    void updateByExampleSelective(@Param("record") T record, @Param("example") BaseExample example);
}

package com.vinesmario.microservice.server.storage.mapper;

import com.vinesmario.microservice.server.common.persistence.mybatis.mapper.CrudMapper;
import com.vinesmario.microservice.server.storage.entity.StorageExcel;

/**
 * @author
 * @date
 */
public interface StorageExcelMapper extends CrudMapper<StorageExcel, Long> {

    StorageExcel selectByUuid(String uuid);

}

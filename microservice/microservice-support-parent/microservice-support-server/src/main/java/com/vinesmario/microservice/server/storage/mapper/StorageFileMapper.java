package com.vinesmario.microservice.server.storage.mapper;

import com.vinesmario.microservice.server.common.persistence.mybatis.mapper.CrudMapper;
import com.vinesmario.microservice.server.storage.entity.StorageFile;

/**
 * @author
 * @date
 */

public interface StorageFileMapper extends CrudMapper<StorageFile, Long> {

    StorageFile selectByUuid(String uuid);

}

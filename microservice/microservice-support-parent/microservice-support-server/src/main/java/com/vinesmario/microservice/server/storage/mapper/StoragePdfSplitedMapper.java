package com.vinesmario.microservice.server.storage.mapper;

import com.vinesmario.microservice.server.common.persistence.mybatis.mapper.CrudMapper;
import com.vinesmario.microservice.server.storage.entity.StoragePdfSplited;

/**
 * @author
 * @date
 */
public interface StoragePdfSplitedMapper extends CrudMapper<StoragePdfSplited, Long> {

    StoragePdfSplited selectByUuid(String uuid);

}

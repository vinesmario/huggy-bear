package com.vinesmario.microservice.server.storage.mapper;

import com.vinesmario.microservice.server.common.persistence.mybatis.mapper.CrudMapper;
import com.vinesmario.microservice.server.storage.entity.StoragePdf;

/**
 * @author
 * @date
 */
public interface StoragePdfMapper extends CrudMapper<StoragePdf, Long> {

    StoragePdf selectByUuid(String uuid);

}

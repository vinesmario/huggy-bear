package com.vinesmario.microservice.server.storage.mapper;

import com.vinesmario.microservice.server.common.persistence.mybatis.mapper.CrudMapper;
import com.vinesmario.microservice.server.storage.entity.StorageTemplate;

/**
 * @author
 * @date
 */
public interface StorageTemplateMapper extends CrudMapper<StorageTemplate, Long> {

    StorageTemplate selectByUuid(String uuid);

}

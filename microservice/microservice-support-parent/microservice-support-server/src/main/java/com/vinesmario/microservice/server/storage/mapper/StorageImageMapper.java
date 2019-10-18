package com.vinesmario.microservice.server.storage.mapper;

import com.vinesmario.microservice.server.common.persistence.mybatis.mapper.CrudMapper;
import com.vinesmario.microservice.server.storage.po.StorageImage;

/**
 * @author
 * @date
 */

public interface StorageImageMapper extends CrudMapper<StorageImage, Long> {

    StorageImage selectByUuid(String uuid);

}

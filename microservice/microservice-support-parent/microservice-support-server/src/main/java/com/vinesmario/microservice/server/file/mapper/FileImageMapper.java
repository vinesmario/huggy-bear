package com.vinesmario.microservice.server.file.mapper;

import com.vinesmario.microservice.server.common.persistence.mybatis.mapper.CrudMapper;
import com.vinesmario.microservice.server.file.entity.FileImage;

/**
 * @author
 * @date
 */

public interface FileImageMapper extends CrudMapper<FileImage, Long>{

    FileImage selectByUuid(String uuid);
}

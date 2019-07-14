package com.vinesmario.microservice.server.storage.mapstruct;

import com.vinesmario.microservice.server.common.mapstruct.BaseMapStruct;
import com.vinesmario.microservice.client.storage.dto.StorageFileDto;
import com.vinesmario.microservice.server.storage.entity.StorageFile;
import org.mapstruct.Mapper;

/**
 * @author
 * @date
 */

@Mapper(componentModel = "spring")
public interface StorageFileMapStruct extends BaseMapStruct<StorageFile, StorageFileDto> {

}

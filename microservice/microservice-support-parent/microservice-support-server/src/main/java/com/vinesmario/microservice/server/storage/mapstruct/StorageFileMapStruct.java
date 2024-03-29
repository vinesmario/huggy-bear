package com.vinesmario.microservice.server.storage.mapstruct;

import com.vinesmario.microservice.server.common.mapstruct.BaseMapStruct;
import com.vinesmario.microservice.client.storage.dto.StorageFileDTO;
import com.vinesmario.microservice.server.storage.po.StorageFile;
import org.mapstruct.Mapper;

/**
 * @author
 * @date
 */

@Mapper(componentModel = "spring")
public interface StorageFileMapStruct extends BaseMapStruct<StorageFile, StorageFileDTO> {

}

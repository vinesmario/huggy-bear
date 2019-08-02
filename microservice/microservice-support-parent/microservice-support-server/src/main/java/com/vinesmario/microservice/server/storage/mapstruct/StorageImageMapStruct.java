package com.vinesmario.microservice.server.storage.mapstruct;

import com.vinesmario.microservice.server.common.mapstruct.BaseMapStruct;
import com.vinesmario.microservice.client.storage.dto.StorageImageDTO;
import com.vinesmario.microservice.server.storage.entity.StorageImage;
import org.mapstruct.Mapper;

/**
 * @author
 * @date
 */

@Mapper(componentModel = "spring")
public interface StorageImageMapStruct extends BaseMapStruct<StorageImage, StorageImageDTO> {

}

package com.vinesmario.microservice.server.storage.mapstruct;

import com.vinesmario.microservice.server.common.mapstruct.BaseMapStruct;
import com.vinesmario.microservice.client.storage.dto.StorageTemplateDTO;
import com.vinesmario.microservice.server.storage.po.StorageTemplate;
import org.mapstruct.Mapper;

/**
 * @author
 * @date
 */
@Mapper(componentModel = "spring")
public interface StorageTemplateMapStruct extends BaseMapStruct<StorageTemplate, StorageTemplateDTO> {

}

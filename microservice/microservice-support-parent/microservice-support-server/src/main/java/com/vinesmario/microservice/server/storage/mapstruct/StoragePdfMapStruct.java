package com.vinesmario.microservice.server.storage.mapstruct;

import com.vinesmario.microservice.server.common.mapstruct.BaseMapStruct;
import com.vinesmario.microservice.client.storage.dto.StoragePdfDTO;
import com.vinesmario.microservice.server.storage.po.StoragePdf;
import org.mapstruct.Mapper;

/**
 * @author
 * @date
 */
@Mapper(componentModel = "spring")
public interface StoragePdfMapStruct extends BaseMapStruct<StoragePdf, StoragePdfDTO> {

}

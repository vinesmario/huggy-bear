package com.vinesmario.microservice.server.storage.mapstruct;

import com.vinesmario.microservice.server.common.mapstruct.BaseMapStruct;
import com.vinesmario.microservice.client.storage.dto.StoragePdfSplitedDTO;
import com.vinesmario.microservice.server.storage.po.StoragePdfSplited;
import org.mapstruct.Mapper;

/**
 * @author
 * @date
 */
@Mapper(componentModel = "spring")
public interface StoragePdfSplitedMapStruct extends BaseMapStruct<StoragePdfSplited, StoragePdfSplitedDTO> {

}

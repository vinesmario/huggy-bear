package com.vinesmario.microservice.server.file.mapstruct;

import com.vinesmario.microservice.server.common.mapstruct.BaseMapStruct;
import com.vinesmario.microservice.client.file.dto.FileImageDto;
import com.vinesmario.microservice.server.file.entity.FileImage;
import org.mapstruct.Mapper;

/**
 * @author
 * @date
 */

@Mapper(componentModel = "spring")
public interface FileImageMapStruct extends BaseMapStruct<FileImage, FileImageDto> {

}

package com.vinesmario.microservice.server.common.mapstruct;

import com.vinesmario.microservice.client.common.dto.BaseDto;
import com.vinesmario.microservice.server.common.entity.BaseEntity;

import java.util.List;

public interface BaseMapStruct<T extends BaseEntity, DTO extends BaseDto> {

    DTO fromEntity2Dto(T entity);

    T fromDto2Entity(DTO dto);

    List<DTO> fromEntities2Dtos(List<T> entities);

    List<T> fromDtos2Entities(List<DTO> dtos);

}

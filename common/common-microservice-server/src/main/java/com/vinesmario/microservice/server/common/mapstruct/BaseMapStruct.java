package com.vinesmario.microservice.server.common.mapstruct;

import com.vinesmario.microservice.client.common.dto.BaseDTO;
import com.vinesmario.microservice.server.common.entity.BaseEntity;

import java.util.List;

public interface BaseMapStruct<T extends BaseEntity, DTO extends BaseDTO> {

    DTO fromEntity2DTO(T entity);

    T fromDTO2Entity(DTO dto);

    List<DTO> fromEntities2DTOs(List<T> entities);

    List<T> fromDTOs2Entities(List<DTO> dtos);

}

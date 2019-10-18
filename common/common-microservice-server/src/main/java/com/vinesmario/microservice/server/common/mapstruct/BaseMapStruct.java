package com.vinesmario.microservice.server.common.mapstruct;

import com.vinesmario.microservice.client.common.dto.BaseDTO;
import com.vinesmario.microservice.server.common.po.BasePO;

import java.util.List;

public interface BaseMapStruct<T extends BasePO, DTO extends BaseDTO> {

    DTO fromPO2DTO(T entity);

    T fromDTO2PO(DTO dto);

    List<DTO> fromPOs2DTOs(List<T> entities);

    List<T> fromDTOs2POs(List<DTO> dtos);

}

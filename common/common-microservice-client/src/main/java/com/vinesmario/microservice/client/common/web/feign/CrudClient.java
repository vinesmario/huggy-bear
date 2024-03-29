package com.vinesmario.microservice.client.common.web.feign;

import com.vinesmario.microservice.client.common.dto.BaseDTO;
import com.vinesmario.microservice.client.common.dto.condition.ConditionDTO;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Only single-level inheritance supported
 * 只支持一层继承
 *
 * @param <DTO>
 * @param <C>
 * @param <PK>
 */
public interface CrudClient<DTO extends BaseDTO, C extends ConditionDTO, PK extends Serializable> {

    ResponseEntity<List<DTO>> search(C conditionDTO);

    ResponseEntity<DTO> get(PK primaryKey);

    ResponseEntity<DTO> create(DTO dto);

    ResponseEntity<DTO> modify(PK primaryKey, DTO dto);

    ResponseEntity<Void> remove(PK primaryKey);

    ResponseEntity<Void> remove(C conditionDTO);

}

package com.vinesmario.microservice.client.common.web.feign;

import com.vinesmario.microservice.client.common.dto.BaseDto;
import com.vinesmario.microservice.client.common.dto.condition.ConditionDto;
import org.springframework.data.domain.Pageable;
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
public interface RetrieveClient<DTO extends BaseDto, C extends ConditionDto, PK extends Serializable> {

    ResponseEntity<List<DTO>> search(C conditionDto);

    ResponseEntity<DTO> get(PK primaryKey);
}

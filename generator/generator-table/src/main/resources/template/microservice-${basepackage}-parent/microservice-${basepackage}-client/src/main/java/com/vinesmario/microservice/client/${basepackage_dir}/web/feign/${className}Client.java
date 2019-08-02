<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package com.vinesmario.microservice.client.${basepackage}.web.feign;

import com.vinesmario.microservice.client.common.web.feign.CrudClient;
import com.vinesmario.microservice.client.${basepackage}.dto.${className}DTO;
import com.vinesmario.microservice.client.${basepackage}.dto.condition.${className}ConditionDTO;
import com.vinesmario.microservice.client.${basepackage}.web.hystrix.${className}FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Only single-level inheritance supported
 * 只支持一层继承
 *
 * @author
 * @date
 */
@FeignClient(name = "microservice-${basepackage}-server", path = "/api/v1/${table.sqlName}", fallbackFactory = ${className}FallbackFactory.class)
public interface ${className}Client extends CrudClient<${className}DTO, ${className}ConditionDTO, Long>{

	@GetMapping("")
	ResponseEntity<List<${className}DTO>> search(@ModelAttribute ${className}ConditionDTO condition);

	@GetMapping("/{id}")
	ResponseEntity<${className}DTO> get(@PathVariable("id") Long id);

	@PostMapping("")
	ResponseEntity<${className}DTO> create(@RequestBody ${className}DTO dto);

	@PutMapping("/{id}")
	ResponseEntity<${className}DTO> modify(@PathVariable("id") Long id,
                           					@RequestBody ${className}DTO dto);

	@DeleteMapping("/{id}")
    ResponseEntity<Void> remove(@PathVariable("id") Long id);

	@DeleteMapping("")
    ResponseEntity<Void> remove(@RequestBody ${className}ConditionDTO condition);
	
}

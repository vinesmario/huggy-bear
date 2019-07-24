<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package com.vinesmario.microservice.client.${basepackage}.web.feign;

import com.vinesmario.microservice.client.common.web.feign.CrudClient;
import com.vinesmario.microservice.client.${basepackage}.dto.${className}Dto;
import com.vinesmario.microservice.client.${basepackage}.dto.condition.${className}ConditionDto;
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
public interface ${className}Client extends CrudClient<${className}Dto, ${className}ConditionDto, Long>{

	@GetMapping("")
	ResponseEntity<List<${className}Dto>> search(@ModelAttribute ${className}ConditionDto condition);

	@GetMapping("/{id}")
	ResponseEntity<${className}Dto> get(@PathVariable("id") Long id);

	@PostMapping("")
	ResponseEntity<${className}Dto> create(@RequestBody ${className}Dto dto);

	@PutMapping("/{id}")
	ResponseEntity<${className}Dto> modify(@PathVariable("id") Long id,
                           					@RequestBody ${className}Dto dto);

	@DeleteMapping("/{id}")
    ResponseEntity<Void> remove(@PathVariable("id") Long id);

	@DeleteMapping("")
    ResponseEntity<Void> remove(@RequestBody ${className}ConditionDto condition);
	
}

<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package com.vinesmario.microservice.client.${basepackage}.web.client;

import com.vinesmario.microservice.client.common.web.feign.CrudClient;
{basepackage}.dto.${className}Dto;
import com.vinesmario.microservice.client.${basepackage}.dto.condition.${className}ConditionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author
 * @date
 */
@FeignClient(name = "microservice-${basepackage}-server", path = "/api/v1/${table.sqlName}")
public interface ${className}Client extends CrudClient<${className}Dto, ${className}ConditionDto, Long>{

	@GetMapping("")
	ResponseEntity<List<${className}Dto>> search(@ModelAttribute ${className}ConditionDto condition,
												Pageable pageable);

	@GetMapping("/{id}")
	ResponseEntity<${className}Dto> get(@PathVariable("id") Long id);

	@PostMapping("")
	ResponseEntity<${className}Dto> create(@RequestBody ${className}Dto ${classNameLower});

	@PutMapping("/{id}")
	ResponseEntity<${className}Dto> modify(@PathVariable("id") Long id,
                           					@RequestBody ${className}Dto ${classNameLower});

	@DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable("id") Long id);
	
}

<#include "/java_copyright.include">
package com.vinesmario.microservice.client.demo;

import com.vinesmario.microservice.client.common.web.feign.CrudClient;
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
@FeignClient(name = "microservice-${basepackage}-server", path = "/api/v1/${basepackage}_demo", fallbackFactory = ${basepackageCap}DemoFallbackFactory.class)
public interface ${basepackageCap}DemoClient extends CrudClient<${basepackageCap}DemoDto, ${basepackageCap}DemoConditionDto, Long>{

	@GetMapping("")
	ResponseEntity<List<${basepackageCap}DemoDto>> search(@ModelAttribute ${basepackageCap}DemoConditionDto condition);

	@GetMapping("/{id}")
	ResponseEntity<${basepackageCap}DemoDto> get(@PathVariable("id") Long id);

	@PostMapping("")
	ResponseEntity<${basepackageCap}DemoDto> create(@RequestBody ${basepackageCap}DemoDto dto);

	@PutMapping("/{id}")
	ResponseEntity<${basepackageCap}DemoDto> modify(@PathVariable("id") Long id,
                           					@RequestBody ${basepackageCap}DemoDto dto);

	@DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable("id") Long id);

	@DeleteMapping("")
    ResponseEntity<Void> delete(@RequestBody ${basepackageCap}DemoConditionDto condition);
	
}

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
public interface ${basepackageCap}DemoClient extends CrudClient<${basepackageCap}DemoDTO, ${basepackageCap}DemoConditionDTO, Long>{

	@GetMapping("")
	ResponseEntity<List<${basepackageCap}DemoDTO>> search(@ModelAttribute ${basepackageCap}DemoConditionDTO condition);

	@GetMapping("/{id}")
	ResponseEntity<${basepackageCap}DemoDTO> get(@PathVariable("id") Long id);

	@PostMapping("")
	ResponseEntity<${basepackageCap}DemoDTO> create(@RequestBody ${basepackageCap}DemoDTO dto);

	@PutMapping("/{id}")
	ResponseEntity<${basepackageCap}DemoDTO> modify(@PathVariable("id") Long id,
                           					@RequestBody ${basepackageCap}DemoDTO dto);

	@DeleteMapping("/{id}")
    ResponseEntity<Void> remove(@PathVariable("id") Long id);

	@DeleteMapping("")
    ResponseEntity<Void> remove(@RequestBody ${basepackageCap}DemoConditionDTO condition);
	
}

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
@FeignClient(name = "microservice-schedule-server", path = "/api/v1/schedule_demo", fallbackFactory = ScheduleDemoFallbackFactory.class)
public interface ScheduleDemoClient extends CrudClient<ScheduleDemoDTO, ScheduleDemoConditionDTO, Long>{

	@GetMapping("")
	ResponseEntity<List<ScheduleDemoDTO>> search(@ModelAttribute ScheduleDemoConditionDTO condition);

	@GetMapping("/{id}")
	ResponseEntity<ScheduleDemoDTO> get(@PathVariable("id") Long id);

	@PostMapping("")
	ResponseEntity<ScheduleDemoDTO> create(@RequestBody ScheduleDemoDTO dto);

	@PutMapping("/{id}")
	ResponseEntity<ScheduleDemoDTO> modify(@PathVariable("id") Long id,
                                           @RequestBody ScheduleDemoDTO dto);

	@DeleteMapping("/{id}")
    ResponseEntity<Void> remove(@PathVariable("id") Long id);

	@DeleteMapping("")
    ResponseEntity<Void> remove(@RequestBody ScheduleDemoConditionDTO condition);
	
}

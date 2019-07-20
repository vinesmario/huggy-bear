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
public interface ScheduleDemoClient extends CrudClient<ScheduleDemoDto, ScheduleDemoConditionDto, Long>{

	@GetMapping("")
	ResponseEntity<List<ScheduleDemoDto>> search(@ModelAttribute ScheduleDemoConditionDto condition);

	@GetMapping("/{id}")
	ResponseEntity<ScheduleDemoDto> get(@PathVariable("id") Long id);

	@PostMapping("")
	ResponseEntity<ScheduleDemoDto> create(@RequestBody ScheduleDemoDto dto);

	@PutMapping("/{id}")
	ResponseEntity<ScheduleDemoDto> modify(@PathVariable("id") Long id,
                                           @RequestBody ScheduleDemoDto dto);

	@DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable("id") Long id);

	@DeleteMapping("")
    ResponseEntity<Void> delete(@RequestBody ScheduleDemoConditionDto condition);
	
}

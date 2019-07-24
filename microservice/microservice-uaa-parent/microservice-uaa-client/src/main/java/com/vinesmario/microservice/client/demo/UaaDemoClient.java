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
@FeignClient(name = "microservice-uaa-server", path = "/api/v1/uaa_demo", fallbackFactory = UaaDemoFallbackFactory.class)
public interface UaaDemoClient extends CrudClient<UaaDemoDto, UaaDemoConditionDto, Long> {

    @GetMapping("")
    ResponseEntity<List<UaaDemoDto>> search(@ModelAttribute UaaDemoConditionDto condition);

    @GetMapping("/{id}")
    ResponseEntity<UaaDemoDto> get(@PathVariable("id") Long id);

    @PostMapping("")
    ResponseEntity<UaaDemoDto> create(@RequestBody UaaDemoDto dto);

    @PutMapping("/{id}")
    ResponseEntity<UaaDemoDto> modify(@PathVariable("id") Long id,
                                      @RequestBody UaaDemoDto dto);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> remove(@PathVariable("id") Long id);

    @DeleteMapping("")
    ResponseEntity<Void> remove(@RequestBody UaaDemoConditionDto conditionDto);

}

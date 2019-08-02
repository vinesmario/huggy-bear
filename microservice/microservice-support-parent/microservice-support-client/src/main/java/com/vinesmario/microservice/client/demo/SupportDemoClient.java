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
@FeignClient(name = "microservice-support-server", path = "/api/v1/support_demo", fallbackFactory = SupportDemoFallbackFactory.class)
public interface SupportDemoClient extends CrudClient<SupportDemoDTO, SupportDemoConditionDTO, Long> {

    @GetMapping("")
    ResponseEntity<List<SupportDemoDTO>> search(@ModelAttribute SupportDemoConditionDTO condition);

    @GetMapping("/{id}")
    ResponseEntity<SupportDemoDTO> get(@PathVariable("id") Long id);

    @PostMapping("")
    ResponseEntity<SupportDemoDTO> create(@RequestBody SupportDemoDTO dto);

    @PutMapping("/{id}")
    ResponseEntity<SupportDemoDTO> modify(@PathVariable("id") Long id,
                                      @RequestBody SupportDemoDTO dto);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> remove(@PathVariable("id") Long id);

    @DeleteMapping("")
    ResponseEntity<Void> remove(@RequestBody SupportDemoConditionDTO conditionDTO);

}

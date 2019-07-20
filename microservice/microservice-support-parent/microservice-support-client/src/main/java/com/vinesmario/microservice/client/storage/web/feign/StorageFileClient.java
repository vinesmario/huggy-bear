package com.vinesmario.microservice.client.storage.web.feign;

import com.vinesmario.microservice.client.common.web.feign.CrudClient;
import com.vinesmario.microservice.client.storage.dto.StorageFileDto;
import com.vinesmario.microservice.client.storage.dto.condition.StorageFileConditionDto;
import com.vinesmario.microservice.client.storage.web.hystrix.StorageFileFallbackFactory;
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
@FeignClient(name = "microservice-support-server", path = "/api/v1/storage_file", fallbackFactory = StorageFileFallbackFactory.class)
public interface StorageFileClient extends CrudClient<StorageFileDto, StorageFileConditionDto, Long>{

	@GetMapping("")
	ResponseEntity<List<StorageFileDto>> search(@ModelAttribute StorageFileConditionDto condition);

	@GetMapping("/{id}")
	ResponseEntity<StorageFileDto> get(@PathVariable("id") Long id);

	@PostMapping("")
	ResponseEntity<StorageFileDto> create(@RequestBody StorageFileDto dto);

	@PutMapping("/{id}")
	ResponseEntity<StorageFileDto> modify(@PathVariable("id") Long id,
                                          @RequestBody StorageFileDto dto);

	@DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable("id") Long id);

	@DeleteMapping("")
    ResponseEntity<Void> delete(@RequestBody StorageFileConditionDto condition);
	
}

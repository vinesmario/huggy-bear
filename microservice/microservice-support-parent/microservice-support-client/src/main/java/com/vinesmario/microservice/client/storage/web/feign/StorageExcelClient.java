package com.vinesmario.microservice.client.storage.web.feign;

import com.vinesmario.microservice.client.common.web.feign.CrudClient;
import com.vinesmario.microservice.client.storage.dto.StorageExcelDto;
import com.vinesmario.microservice.client.storage.dto.condition.StorageExcelConditionDto;
import com.vinesmario.microservice.client.storage.web.hystrix.StorageExcelFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Only single-level inheritance supported
 * 只支持一层继承
 *
 * @author
 * @date
 */
@FeignClient(name = "microservice-support-server", path = "/api/v1/storage_excel", fallbackFactory = StorageExcelFallbackFactory.class)
public interface StorageExcelClient extends CrudClient<StorageExcelDto, StorageExcelConditionDto, Long>{

	@GetMapping("")
	ResponseEntity<List<StorageExcelDto>> search(@ModelAttribute StorageExcelConditionDto condition);

	@GetMapping("/{id}")
	ResponseEntity<StorageExcelDto> get(@PathVariable("id") Long id);

	@PostMapping("")
	ResponseEntity<StorageExcelDto> create(@RequestBody StorageExcelDto dto);

	@PutMapping("/{id}")
	ResponseEntity<StorageExcelDto> modify(@PathVariable("id") Long id,
                                           @RequestBody StorageExcelDto dto);

	@DeleteMapping("/{id}")
    ResponseEntity<Void> remove(@PathVariable("id") Long id);

	@DeleteMapping("")
    ResponseEntity<Void> remove(@RequestBody StorageExcelConditionDto condition);

	ResponseEntity<StorageExcelDto> upload(@RequestParam(value = "file", required = false) MultipartFile multipartFile,
										   @RequestParam(value = "tenantId", required = false) Long tenantId,
										   @RequestParam(value = "memo", required = false) String memo)
			throws Exception;

	ResponseEntity<byte[]> download(@PathVariable("uuid") String uuid)
			throws IOException;

}

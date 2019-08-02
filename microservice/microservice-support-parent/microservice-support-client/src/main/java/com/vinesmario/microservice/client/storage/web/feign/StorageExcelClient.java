package com.vinesmario.microservice.client.storage.web.feign;

import com.vinesmario.microservice.client.common.web.feign.CrudClient;
import com.vinesmario.microservice.client.storage.dto.StorageExcelDTO;
import com.vinesmario.microservice.client.storage.dto.condition.StorageExcelConditionDTO;
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
public interface StorageExcelClient extends CrudClient<StorageExcelDTO, StorageExcelConditionDTO, Long>{

	@GetMapping("")
	ResponseEntity<List<StorageExcelDTO>> search(@ModelAttribute StorageExcelConditionDTO condition);

	@GetMapping("/{id}")
	ResponseEntity<StorageExcelDTO> get(@PathVariable("id") Long id);

	@PostMapping("")
	ResponseEntity<StorageExcelDTO> create(@RequestBody StorageExcelDTO dto);

	@PutMapping("/{id}")
	ResponseEntity<StorageExcelDTO> modify(@PathVariable("id") Long id,
                                           @RequestBody StorageExcelDTO dto);

	@DeleteMapping("/{id}")
    ResponseEntity<Void> remove(@PathVariable("id") Long id);

	@DeleteMapping("")
    ResponseEntity<Void> remove(@RequestBody StorageExcelConditionDTO condition);

	ResponseEntity<StorageExcelDTO> upload(@RequestParam(value = "file", required = false) MultipartFile multipartFile,
										   @RequestParam(value = "tenantId", required = false) Long tenantId,
										   @RequestParam(value = "memo", required = false) String memo)
			throws Exception;

	ResponseEntity<byte[]> download(@PathVariable("uuid") String uuid)
			throws IOException;

}

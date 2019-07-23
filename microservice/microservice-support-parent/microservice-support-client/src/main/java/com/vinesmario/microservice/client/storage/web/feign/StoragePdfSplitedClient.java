package com.vinesmario.microservice.client.storage.web.feign;

import com.vinesmario.microservice.client.common.web.feign.CrudClient;
import com.vinesmario.microservice.client.storage.dto.StoragePdfSplitedDto;
import com.vinesmario.microservice.client.storage.dto.condition.StoragePdfSplitedConditionDto;
import com.vinesmario.microservice.client.storage.web.hystrix.StoragePdfSplitedFallbackFactory;
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
@FeignClient(name = "microservice-support-server", path = "/api/v1/storage_pdf_splited", fallbackFactory = StoragePdfSplitedFallbackFactory.class)
public interface StoragePdfSplitedClient extends CrudClient<StoragePdfSplitedDto, StoragePdfSplitedConditionDto, Long>{

	@GetMapping("")
	ResponseEntity<List<StoragePdfSplitedDto>> search(@ModelAttribute StoragePdfSplitedConditionDto condition);

	@GetMapping("/{id}")
	ResponseEntity<StoragePdfSplitedDto> get(@PathVariable("id") Long id);

	@PostMapping("")
	ResponseEntity<StoragePdfSplitedDto> create(@RequestBody StoragePdfSplitedDto dto);

	@PutMapping("/{id}")
	ResponseEntity<StoragePdfSplitedDto> modify(@PathVariable("id") Long id,
                                                @RequestBody StoragePdfSplitedDto dto);

	@DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable("id") Long id);

	@DeleteMapping("")
    ResponseEntity<Void> delete(@RequestBody StoragePdfSplitedConditionDto condition);

	ResponseEntity<StoragePdfSplitedDto> upload(@RequestParam(value = "file", required = false) MultipartFile multipartFile,
										 @RequestParam(value = "tenantId", required = false) Long tenantId,
										 @RequestParam(value = "memo", required = false) String memo)
			throws Exception;

	ResponseEntity<byte[]> download(@PathVariable("uuid") String uuid)
			throws IOException;

}

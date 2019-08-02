package com.vinesmario.microservice.client.storage.web.feign;

import com.vinesmario.microservice.client.common.web.feign.CrudClient;
import com.vinesmario.microservice.client.storage.dto.StoragePdfSplitedDTO;
import com.vinesmario.microservice.client.storage.dto.condition.StoragePdfSplitedConditionDTO;
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
public interface StoragePdfSplitedClient extends CrudClient<StoragePdfSplitedDTO, StoragePdfSplitedConditionDTO, Long>{

	@GetMapping("")
	ResponseEntity<List<StoragePdfSplitedDTO>> search(@ModelAttribute StoragePdfSplitedConditionDTO condition);

	@GetMapping("/{id}")
	ResponseEntity<StoragePdfSplitedDTO> get(@PathVariable("id") Long id);

	@PostMapping("")
	ResponseEntity<StoragePdfSplitedDTO> create(@RequestBody StoragePdfSplitedDTO dto);

	@PutMapping("/{id}")
	ResponseEntity<StoragePdfSplitedDTO> modify(@PathVariable("id") Long id,
                                                @RequestBody StoragePdfSplitedDTO dto);

	@DeleteMapping("/{id}")
    ResponseEntity<Void> remove(@PathVariable("id") Long id);

	@DeleteMapping("")
    ResponseEntity<Void> remove(@RequestBody StoragePdfSplitedConditionDTO condition);

	ResponseEntity<StoragePdfSplitedDTO> upload(@RequestParam(value = "file", required = false) MultipartFile multipartFile,
										 @RequestParam(value = "tenantId", required = false) Long tenantId,
										 @RequestParam(value = "memo", required = false) String memo)
			throws Exception;

	ResponseEntity<byte[]> download(@PathVariable("uuid") String uuid)
			throws IOException;

}

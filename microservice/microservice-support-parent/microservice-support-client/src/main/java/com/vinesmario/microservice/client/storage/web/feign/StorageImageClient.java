package com.vinesmario.microservice.client.storage.web.feign;

import com.vinesmario.microservice.client.common.web.feign.CrudClient;
import com.vinesmario.microservice.client.storage.dto.StorageImageDTO;
import com.vinesmario.microservice.client.storage.dto.condition.StorageImageConditionDTO;
import com.vinesmario.microservice.client.storage.web.hystrix.StorageImageFallbackFactory;
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
@FeignClient(name = "microservice-support-server", path = "/api/v1/storage_image", fallbackFactory = StorageImageFallbackFactory.class)
public interface StorageImageClient extends CrudClient<StorageImageDTO, StorageImageConditionDTO, Long> {

    @GetMapping("")
    ResponseEntity<List<StorageImageDTO>> search(@ModelAttribute StorageImageConditionDTO condition);

    @GetMapping("/{id}")
    ResponseEntity<StorageImageDTO> get(@PathVariable("id") Long id);

    @PostMapping("")
    ResponseEntity<StorageImageDTO> create(@RequestBody StorageImageDTO dto);

    @PutMapping("/{id}")
    ResponseEntity<StorageImageDTO> modify(@PathVariable("id") Long id, @RequestBody StorageImageDTO dto);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> remove(@PathVariable("id") Long id);

    @DeleteMapping("")
    ResponseEntity<Void> remove(@RequestBody StorageImageConditionDTO condition);

    ResponseEntity<StorageImageDTO> upload(@RequestParam(value = "file", required = false) MultipartFile multipartFile,
                                           @RequestParam(value = "tenantId", required = false) Long tenantId,
                                           @RequestParam(value = "memo", required = false) String memo)
            throws Exception;

    ResponseEntity<byte[]> download(@PathVariable("uuid") String uuid)
            throws IOException;

}

package com.vinesmario.microservice.client.storage.web.feign;

import com.vinesmario.microservice.client.common.web.feign.CrudClient;
import com.vinesmario.microservice.client.storage.dto.StoragePdfDTO;
import com.vinesmario.microservice.client.storage.dto.condition.StoragePdfConditionDTO;
import com.vinesmario.microservice.client.storage.web.hystrix.StoragePdfFallbackFactory;
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
@FeignClient(name = "microservice-support-server", path = "/api/v1/storage/pdf", fallbackFactory = StoragePdfFallbackFactory.class)
public interface StoragePdfClient extends CrudClient<StoragePdfDTO, StoragePdfConditionDTO, Long> {

    @GetMapping("")
    ResponseEntity<List<StoragePdfDTO>> search(@ModelAttribute StoragePdfConditionDTO condition);

    @GetMapping("/{id}")
    ResponseEntity<StoragePdfDTO> get(@PathVariable("id") Long id);

    @PostMapping("")
    ResponseEntity<StoragePdfDTO> create(@RequestBody StoragePdfDTO dto);

    @PutMapping("/{id}")
    ResponseEntity<StoragePdfDTO> modify(@PathVariable("id") Long id,
                                         @RequestBody StoragePdfDTO dto);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> remove(@PathVariable("id") Long id);

    @DeleteMapping("")
    ResponseEntity<Void> remove(@RequestBody StoragePdfConditionDTO condition);

    ResponseEntity<StoragePdfDTO> upload(@RequestParam(value = "file", required = false) MultipartFile multipartFile,
                                         @RequestParam(value = "tenantId", required = false) Long tenantId,
                                         @RequestParam(value = "memo", required = false) String memo)
            throws Exception;

    ResponseEntity<byte[]> download(@PathVariable("uuid") String uuid)
            throws IOException;

}

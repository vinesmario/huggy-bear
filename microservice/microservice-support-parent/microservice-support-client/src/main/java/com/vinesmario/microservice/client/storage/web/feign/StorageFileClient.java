package com.vinesmario.microservice.client.storage.web.feign;

import com.vinesmario.microservice.client.common.web.feign.CrudClient;
import com.vinesmario.microservice.client.storage.dto.StorageFileDTO;
import com.vinesmario.microservice.client.storage.dto.condition.StorageFileConditionDTO;
import com.vinesmario.microservice.client.storage.web.hystrix.StorageFileFallbackFactory;
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
@FeignClient(name = "microservice-support-server", path = "/api/v1/storage/file", fallbackFactory = StorageFileFallbackFactory.class)
public interface StorageFileClient extends CrudClient<StorageFileDTO, StorageFileConditionDTO, Long> {

    @GetMapping("")
    ResponseEntity<List<StorageFileDTO>> search(@ModelAttribute StorageFileConditionDTO condition);

    @GetMapping("/{id}")
    ResponseEntity<StorageFileDTO> get(@PathVariable("id") Long id);

    @PostMapping("")
    ResponseEntity<StorageFileDTO> create(@RequestBody StorageFileDTO dto);

    @PutMapping("/{id}")
    ResponseEntity<StorageFileDTO> modify(@PathVariable("id") Long id,
                                          @RequestBody StorageFileDTO dto);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> remove(@PathVariable("id") Long id);

    @DeleteMapping("")
    ResponseEntity<Void> remove(@RequestBody StorageFileConditionDTO condition);

    ResponseEntity<StorageFileDTO> upload(@RequestParam(value = "file", required = false) MultipartFile multipartFile,
                                          @RequestParam(value = "tenantId", required = false) Long tenantId,
                                          @RequestParam(value = "memo", required = false) String memo)
            throws Exception;

    ResponseEntity<byte[]> download(@PathVariable("uuid") String uuid)
            throws IOException;

}

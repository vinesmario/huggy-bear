package com.vinesmario.microservice.client.storage.web.feign;

import com.vinesmario.microservice.client.common.web.feign.CrudClient;
import com.vinesmario.microservice.client.storage.dto.StorageTemplateDTO;
import com.vinesmario.microservice.client.storage.dto.condition.StorageTemplateConditionDTO;
import com.vinesmario.microservice.client.storage.web.hystrix.StorageTemplateFallbackFactory;
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
@FeignClient(name = "microservice-support-server", path = "/api/v1/storage/template", fallbackFactory = StorageTemplateFallbackFactory.class)
public interface StorageTemplateClient extends CrudClient<StorageTemplateDTO, StorageTemplateConditionDTO, Long> {

    @GetMapping("")
    ResponseEntity<List<StorageTemplateDTO>> search(@ModelAttribute StorageTemplateConditionDTO condition);

    @GetMapping("/{id}")
    ResponseEntity<StorageTemplateDTO> get(@PathVariable("id") Long id);

    @PostMapping("")
    ResponseEntity<StorageTemplateDTO> create(@RequestBody StorageTemplateDTO dto);

    @PutMapping("/{id}")
    ResponseEntity<StorageTemplateDTO> modify(@PathVariable("id") Long id,
                                              @RequestBody StorageTemplateDTO dto);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> remove(@PathVariable("id") Long id);

    @DeleteMapping("")
    ResponseEntity<Void> remove(@RequestBody StorageTemplateConditionDTO condition);

    ResponseEntity<StorageTemplateDTO> upload(@RequestParam(value = "file", required = false) MultipartFile multipartFile,
                                              @RequestParam(value = "tenantId", required = false) Long tenantId,
                                              @RequestParam(value = "memo", required = false) String memo)
            throws Exception;

    ResponseEntity<byte[]> download(@PathVariable("uuid") String uuid)
            throws IOException;

}

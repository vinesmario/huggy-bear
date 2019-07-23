package com.vinesmario.microservice.client.storage.web.feign;

import com.vinesmario.microservice.client.common.web.feign.CrudClient;
import com.vinesmario.microservice.client.storage.dto.StorageTemplateDto;
import com.vinesmario.microservice.client.storage.dto.condition.StorageTemplateConditionDto;
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
@FeignClient(name = "microservice-support-server", path = "/api/v1/storage_template", fallbackFactory = StorageTemplateFallbackFactory.class)
public interface StorageTemplateClient extends CrudClient<StorageTemplateDto, StorageTemplateConditionDto, Long> {

    @GetMapping("")
    ResponseEntity<List<StorageTemplateDto>> search(@ModelAttribute StorageTemplateConditionDto condition);

    @GetMapping("/{id}")
    ResponseEntity<StorageTemplateDto> get(@PathVariable("id") Long id);

    @PostMapping("")
    ResponseEntity<StorageTemplateDto> create(@RequestBody StorageTemplateDto dto);

    @PutMapping("/{id}")
    ResponseEntity<StorageTemplateDto> modify(@PathVariable("id") Long id,
                                              @RequestBody StorageTemplateDto dto);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable("id") Long id);

    @DeleteMapping("")
    ResponseEntity<Void> delete(@RequestBody StorageTemplateConditionDto condition);

    ResponseEntity<StorageTemplateDto> upload(@RequestParam(value = "file", required = false) MultipartFile multipartFile,
                                              @RequestParam(value = "tenantId", required = false) Long tenantId,
                                              @RequestParam(value = "memo", required = false) String memo)
            throws Exception;

    ResponseEntity<byte[]> download(@PathVariable("uuid") String uuid)
            throws IOException;

}

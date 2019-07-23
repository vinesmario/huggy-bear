package com.vinesmario.microservice.client.storage.web.feign;

import com.vinesmario.microservice.client.common.web.feign.CrudClient;
import com.vinesmario.microservice.client.storage.dto.StoragePdfDto;
import com.vinesmario.microservice.client.storage.dto.condition.StoragePdfConditionDto;
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
@FeignClient(name = "microservice-support-server", path = "/api/v1/storage_pdf", fallbackFactory = StoragePdfFallbackFactory.class)
public interface StoragePdfClient extends CrudClient<StoragePdfDto, StoragePdfConditionDto, Long> {

    @GetMapping("")
    ResponseEntity<List<StoragePdfDto>> search(@ModelAttribute StoragePdfConditionDto condition);

    @GetMapping("/{id}")
    ResponseEntity<StoragePdfDto> get(@PathVariable("id") Long id);

    @PostMapping("")
    ResponseEntity<StoragePdfDto> create(@RequestBody StoragePdfDto dto);

    @PutMapping("/{id}")
    ResponseEntity<StoragePdfDto> modify(@PathVariable("id") Long id,
                                         @RequestBody StoragePdfDto dto);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable("id") Long id);

    @DeleteMapping("")
    ResponseEntity<Void> delete(@RequestBody StoragePdfConditionDto condition);

    ResponseEntity<StoragePdfDto> upload(@RequestParam(value = "file", required = false) MultipartFile multipartFile,
                                         @RequestParam(value = "tenantId", required = false) Long tenantId,
                                         @RequestParam(value = "memo", required = false) String memo)
            throws Exception;

    ResponseEntity<byte[]> download(@PathVariable("uuid") String uuid)
            throws IOException;

}

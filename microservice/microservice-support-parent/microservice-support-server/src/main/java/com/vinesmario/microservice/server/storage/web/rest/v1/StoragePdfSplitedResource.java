package com.vinesmario.microservice.server.storage.web.rest.v1;

import com.vinesmario.microservice.client.storage.dto.StoragePdfSplitedDto;
import com.vinesmario.microservice.client.storage.dto.condition.StoragePdfSplitedConditionDto;
import com.vinesmario.microservice.client.storage.dto.StoragePdfSplitedDto;
import com.vinesmario.microservice.client.storage.web.feign.StoragePdfClient;
import com.vinesmario.microservice.client.storage.web.feign.StoragePdfSplitedClient;
import com.vinesmario.microservice.server.common.web.rest.BaseResource;
import com.vinesmario.microservice.server.common.web.rest.errors.BadRequestAlertException;
import com.vinesmario.microservice.server.common.web.rest.util.HeaderUtil;
import com.vinesmario.microservice.server.common.web.rest.util.ResponseUtil;
import com.vinesmario.microservice.server.storage.factory.AbstractStorageFactory;
import com.vinesmario.microservice.server.storage.factory.StoragePdfSplitedFactory;
import com.vinesmario.microservice.server.storage.service.StoragePdfSplitedService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * @author
 * @date
 */
@Api(description = "StoragePdfSplitedCRUD", tags = "StoragePdfSplitedController", basePath = "/storage_pdf_splited")
@Slf4j
@RestController
@RequestMapping("/api/v1/storage_pdf_splited")
public class StoragePdfSplitedResource extends BaseResource<StoragePdfSplitedDto, StoragePdfSplitedConditionDto, Long>
        implements StoragePdfSplitedClient {

    private final StoragePdfSplitedService service;

    public StoragePdfSplitedResource(StoragePdfSplitedService service) {
        super(service);
        this.service = service;
        this.entityName = "StoragePdfSplited";
    }

    @Override
    public void preConditionDto(StoragePdfSplitedConditionDto queryDto) {

    }

    @ApiOperation(value = "查找明细", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "查询成功", response = String.class)
    @GetMapping(value = "/uuid/{uuid}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<StoragePdfSplitedDto> getByUuid(@PathVariable("uuid") String uuid) {
        Optional<StoragePdfSplitedDto> dto = service.getByUuid(uuid);

        return ResponseUtil.wrapOrNotFound(dto);
    }

    @ApiOperation(value = "添加", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "添加成功", response = String.class)
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<StoragePdfSplitedDto> create(@RequestBody StoragePdfSplitedDto dto) {
        // 不需支持该方法
        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "上传文件", httpMethod = "POST")
    @ApiResponse(code = 200, message = "上传文件成功", response = String.class)
    @PostMapping(value = "/upload")
    @ResponseBody
    public ResponseEntity<StoragePdfSplitedDto> upload(@RequestParam(value = "file", required = false) MultipartFile multipartFile,
                                                 @RequestParam(value = "tenantId", required = false) Long tenantId,
                                                 @RequestParam(value = "memo", required = false) String memo)
            throws Exception {
        if (ObjectUtils.isEmpty(multipartFile) || multipartFile.isEmpty()) {
            throw new BadRequestAlertException("File cannot be empty",
                    null, "image.empty", entityName);
        }
        AbstractStorageFactory<StoragePdfSplitedDto> storageFactory = new StoragePdfSplitedFactory();
        StoragePdfSplitedDto storageFileDto = storageFactory.create(multipartFile, tenantId);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityCreationAlert(entityName, storageFileDto.getAlertParam()))
                .body(storageFileDto);
    }

    @ApiOperation(value = "下载文件", httpMethod = "GET")
    @ApiResponse(code = 200, message = "下载文件成功", response = String.class)
    @GetMapping(value = "/download/{uuid}")
    @ResponseBody
    public ResponseEntity<byte[]> download(@PathVariable("uuid") String uuid)
            throws IOException {
        Optional<StoragePdfSplitedDto> optional = service.getByUuid(uuid);
        if (!optional.isPresent()) {
            return ResponseEntity.notFound()
                    .headers(HeaderUtil.createFailureAlert("record not found", 404, "record.not_found", entityName))
                    .build();
        } else {
            String fileAbsolutePath = optional.get().getFileAbsolutePath();
            if (StringUtils.isBlank(fileAbsolutePath)) {
                return ResponseEntity.notFound()
                        .headers(HeaderUtil.createFailureAlert("file path is empty", 404, "file_path.empty", entityName))
                        .build();
            } else {
                File file = new File(fileAbsolutePath);
                if (!file.exists()) {
                    return ResponseEntity.notFound()
                            .headers(HeaderUtil.createFailureAlert("file not found", 404, "file.not_found", entityName))
                            .build();
                }
                return ResponseEntity.ok()
                        .headers(HeaderUtil.createDownload(file.getName()))
                        .body(FileUtils.readFileToByteArray(file));
            }
        }
    }
    
}

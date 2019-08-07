package com.vinesmario.microservice.server.storage.web.rest.v1;

import com.vinesmario.microservice.client.storage.dto.StorageExcelDTO;
import com.vinesmario.microservice.client.storage.dto.condition.StorageExcelConditionDTO;
import com.vinesmario.microservice.client.storage.web.feign.StorageExcelClient;
import com.vinesmario.microservice.server.common.web.rest.BaseResource;
import com.vinesmario.microservice.server.common.web.rest.errors.BadRequestAlertException;
import com.vinesmario.microservice.server.common.web.rest.util.HeaderUtil;
import com.vinesmario.microservice.server.common.web.rest.util.ResponseUtil;
import com.vinesmario.microservice.server.storage.factory.AbstractStorageFactory;
import com.vinesmario.microservice.server.storage.factory.StorageExcelFactory;
import com.vinesmario.microservice.server.storage.service.StorageExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
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
@Api(description = "StorageExcelCRUD", tags = "StorageExcelController", basePath = "/storage/excel")
@Slf4j
@RestController
@RequestMapping("/api/v1/storage/excel")
public class StorageExcelResource extends BaseResource<StorageExcelDTO, StorageExcelConditionDTO, Long>
        implements StorageExcelClient {

    private final StorageExcelService service;

    public StorageExcelResource(StorageExcelService service) {
        super(service);
        this.service = service;
        this.entityName = "StorageExcel";
    }

    @Override
    public void preConditionDTO(StorageExcelConditionDTO queryDTO) {

    }

    @ApiOperation(value = "查找明细", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "查询成功", response = String.class)
    @GetMapping(value = "/uuid/{uuid}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<StorageExcelDTO> getByUuid(@PathVariable("uuid") String uuid) {
        Optional<StorageExcelDTO> dto = service.getByUuid(uuid);

        return ResponseUtil.wrapOrNotFound(dto);
    }

    @ApiOperation(value = "添加", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "添加成功", response = String.class)
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<StorageExcelDTO> create(@RequestBody StorageExcelDTO dto) {
        // 不需支持该方法
        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "上传Excel", httpMethod = "POST")
    @ApiResponse(code = 200, message = "上传Excel成功", response = String.class)
//    @PreAuthorize("hasPermission(Object target, Object permission)")
    @PostMapping(value = "/upload")
    @ResponseBody
    public ResponseEntity<StorageExcelDTO> upload(@RequestParam(value = "file", required = false) MultipartFile multipartFile,
                                                  @RequestParam(value = "tenantId", required = false) Long tenantId,
                                                  @RequestParam(value = "memo", required = false) String memo)
            throws Exception {
        if (ObjectUtils.isEmpty(multipartFile) || multipartFile.isEmpty()) {
            throw new BadRequestAlertException("File cannot be empty",
                    null, "file.empty", entityName);
        }
        AbstractStorageFactory<StorageExcelDTO> storageFactory = new StorageExcelFactory();
        StorageExcelDTO storageFileDTO = storageFactory.create(multipartFile, tenantId);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityCreationAlert(entityName, storageFileDTO.getAlertParam()))
                .body(storageFileDTO);
    }

    @ApiOperation(value = "下载Excel", httpMethod = "GET")
    @ApiResponse(code = 200, message = "下载Excel成功", response = String.class)
    @GetMapping(value = "/download/{uuid}")
    @ResponseBody
    public ResponseEntity<byte[]> download(@PathVariable("uuid") String uuid)
            throws IOException {
        Optional<StorageExcelDTO> optional = service.getByUuid(uuid);
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

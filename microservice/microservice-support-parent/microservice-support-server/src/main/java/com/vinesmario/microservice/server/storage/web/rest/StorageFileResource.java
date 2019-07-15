package com.vinesmario.microservice.server.storage.web.rest;

import com.vinesmario.microservice.client.storage.dto.StorageFileDto;
import com.vinesmario.microservice.client.storage.dto.condition.StorageFileConditionDto;
import com.vinesmario.microservice.server.common.web.rest.BaseResource;
import com.vinesmario.microservice.server.common.web.rest.errors.BadRequestAlertException;
import com.vinesmario.microservice.server.common.web.rest.util.HeaderUtil;
import com.vinesmario.microservice.server.common.web.rest.util.ResponseUtil;
import com.vinesmario.microservice.server.storage.service.StorageFileService;
import com.vinesmario.microservice.server.storage.strategy.AbstractStorageService;
import com.vinesmario.microservice.server.storage.strategy.StorageServiceFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

/**
 * @author
 * @date
 */

@Api(description = "StorageFileCRUD", tags = "StorageFileController", basePath = "/storage_file")
@Slf4j
@RestController
@RequestMapping("/api/v1/storage_file")
public class StorageFileResource extends BaseResource<StorageFileDto, StorageFileConditionDto, Long> {

    private final StorageFileService service;

    public StorageFileResource(StorageFileService service) {
        super(service);
        this.service = service;
        this.entityName = "StorageFile";
    }

    @Override
    public void preConditionDto(StorageFileConditionDto queryDto) {

    }

    @ApiOperation(value = "查找明细", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "查询成功", response = String.class)
    @GetMapping(value = "/uuid/{uuid}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<StorageFileDto> getByUuid(@PathVariable("uuid") String uuid) {
        Optional<StorageFileDto> dto = service.getByUuid(uuid);

        return ResponseUtil.wrapOrNotFound(dto);
    }

    @ApiOperation(value = "添加", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "添加成功", response = String.class)
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<StorageFileDto> create(@RequestBody StorageFileDto dto){
        // 不需支持该方法
        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "上传文件", httpMethod = "POST")
    @ApiResponse(code = 200, message = "上传文件成功", response = String.class)
//    @PreAuthorize("hasPermission(Object target, Object permission)")
    @PostMapping(value = "/upload")
    @ResponseBody
    public ResponseEntity<StorageFileDto> upload(@RequestParam(value = "file", required = false) MultipartFile multipartFile,
                                                 @RequestParam(value = "tenantId", required = false) Long tenantId,
                                                 @RequestParam(value = "memo", required = false) String memo)
            throws Exception {
        if (ObjectUtils.isEmpty(multipartFile) || multipartFile.isEmpty()) {
            throw new BadRequestAlertException("File cannot be empty",
                    null, "image.empty", entityName);
        }
        AbstractStorageService storageService = StorageServiceFactory.build();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String imageName = uuid + "." + extension;
        String imageRelativePath = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
                + "/" + imageName;
        if (!ObjectUtils.isEmpty(tenantId)) {
            imageRelativePath = tenantId + "/" + imageRelativePath;
        }
        StorageFileDto storageFileDto = new StorageFileDto();
        storageFileDto.setTenantId(tenantId);
        storageFileDto.setUuid(uuid);
        storageFileDto.setFileExtension(extension);
        storageFileDto.setFileName(imageName);
        storageFileDto.setFileSize(multipartFile.getSize());
        // 文件MD5、SHA1
        String md5Hex = DigestUtils.md5Hex(multipartFile.getInputStream());
        String sha1Hex = DigestUtils.sha1Hex(multipartFile.getInputStream());
        storageFileDto.setFileMd5Hex(md5Hex);
        storageFileDto.setFileSha1Hex(sha1Hex);

        storageService.upload(multipartFile, imageRelativePath, storageFileDto);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityCreationAlert(entityName, storageFileDto.getAlertParam()))
                .body(storageFileDto);
    }

    @ApiOperation(value = "下载文件", httpMethod = "GET")
    @ApiResponse(code = 200, message = "下载文件成功", response = String.class)
    @GetMapping(value = "/download/{uuid}")
    @ResponseBody
    public ResponseEntity<byte[]> download(@PathVariable("uuid") String uuid)
            throws IOException
    {
        Optional<StorageFileDto> optional = service.getByUuid(uuid);
        if(!optional.isPresent()){
            return ResponseEntity.notFound()
                    .headers(HeaderUtil.createFailureAlert("record not found",404,"record.not_found",entityName))
                    .build();
        } else {
            String fileAbsolutePath = optional.get().getFileAbsolutePath();
            if(StringUtils.isBlank(fileAbsolutePath)){
                return ResponseEntity.notFound()
                        .headers(HeaderUtil.createFailureAlert("file path is empty",404,"file_path.empty",entityName))
                        .build();
            } else {
                File file = new File(fileAbsolutePath);
                if(!file.exists()){
                    return ResponseEntity.notFound()
                            .headers(HeaderUtil.createFailureAlert("file not found",404,"file.not_found",entityName))
                            .build();
                }
                return ResponseEntity.ok()
                        .headers(HeaderUtil.createDownload(file.getName()))
                        .body(FileUtils.readFileToByteArray(file));
            }
        }
    }
}

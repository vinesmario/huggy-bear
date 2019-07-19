package com.vinesmario.microservice.server.storage.web.rest;

import com.vinesmario.microservice.client.common.security.ServiceTokenEndpointClient;
import com.vinesmario.microservice.client.storage.dto.StorageFileDto;
import com.vinesmario.microservice.client.storage.dto.condition.StorageFileConditionDto;
import com.vinesmario.microservice.client.uaa.dto.UserAccountDto;
import com.vinesmario.microservice.client.uaa.dto.condition.UserAccountConditionDto;
import com.vinesmario.microservice.client.uaa.web.feign.UserAccountClient;
import com.vinesmario.microservice.server.common.web.rest.BaseResource;
import com.vinesmario.microservice.server.common.web.rest.errors.BadRequestAlertException;
import com.vinesmario.microservice.server.common.web.rest.util.HeaderUtil;
import com.vinesmario.microservice.server.common.web.rest.util.ResponseUtil;
import com.vinesmario.microservice.server.storage.factory.AbstractStorageFactory;
import com.vinesmario.microservice.server.storage.factory.StorageFileFactory;
import com.vinesmario.microservice.server.storage.service.StorageFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author
 * @date
 */

@Api(description = "StorageFileCRUD", tags = "StorageFileController", basePath = "/storage_file")
@Slf4j
@RestController
@RequestMapping("/api/v1/storage_file")
public class StorageFileResource extends BaseResource<StorageFileDto, StorageFileConditionDto, Long> {

    @Autowired
    private UserAccountClient userAccountClient;

    private final StorageFileService service;

    public StorageFileResource(StorageFileService service) {
        super(service);
        this.service = service;
        this.entityName = "StorageFile";
    }

    @Override
    public void preConditionDto(StorageFileConditionDto queryDto) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        log.info("AccessToken: " + ((OAuth2AuthenticationDetails) authentication.getDetails()).getTokenValue());
        UserAccountConditionDto conditionDto = new UserAccountConditionDto();
        conditionDto.setPageNumber(0);
        conditionDto.setPageSize(10);
        userAccountClient.search(conditionDto);
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
    public ResponseEntity<StorageFileDto> create(@RequestBody StorageFileDto dto) {
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
        AbstractStorageFactory<StorageFileDto> storageFactory = new StorageFileFactory();
        StorageFileDto storageFileDto = storageFactory.create(multipartFile, tenantId);
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
        Optional<StorageFileDto> optional = service.getByUuid(uuid);
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

//    @Autowired
//    private ServiceTokenEndpointClient serviceTokenEndpointClient;
//    @Autowired
//    private OAuth2ClientContext oauth2ClientContext;
//
//    @PostConstruct
//    public void init() {
//        OAuth2AccessToken accessToken = serviceTokenEndpointClient.sendClentCredentialsGrant();
//        log.info("AccessToken: " + accessToken.getValue());
//        oauth2ClientContext.setAccessToken(accessToken);
//    }
//
//    @Scheduled(cron = "0/10 * * * * ?")
//    public void test() {
//        UserAccountConditionDto conditionDto = new UserAccountConditionDto();
//        conditionDto.setPageNumber(0);
//        conditionDto.setPageSize(10);
//        ResponseEntity<List<UserAccountDto>> responseEntity = userAccountClient.search(conditionDto);
//        log.info("X-Total-Count: "+responseEntity.getHeaders().get("X-Total-Count").get(0));
//    }
}

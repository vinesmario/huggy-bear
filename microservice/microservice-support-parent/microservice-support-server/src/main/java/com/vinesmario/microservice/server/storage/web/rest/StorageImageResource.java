package com.vinesmario.microservice.server.storage.web.rest;

import com.vinesmario.common.constant.FileExtension;
import com.vinesmario.microservice.client.storage.dto.StorageImageDto;
import com.vinesmario.microservice.client.storage.dto.condition.StorageImageConditionDto;
import com.vinesmario.microservice.server.common.web.rest.BaseResource;
import com.vinesmario.microservice.server.common.web.rest.errors.BadRequestAlertException;
import com.vinesmario.microservice.server.common.web.rest.util.HeaderUtil;
import com.vinesmario.microservice.server.common.web.rest.util.ResponseUtil;
import com.vinesmario.microservice.server.storage.service.StorageImageService;
import com.vinesmario.microservice.server.storage.strategy.StorageStrategy;
import com.vinesmario.microservice.server.storage.strategy.StorageStrategyFactory;
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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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

@Api(description = "StorageImageCRUD", tags = "StorageImageController", basePath = "/storage_image")
@Slf4j
@RestController
@RequestMapping("/api/v1/storage_image")
public class StorageImageResource extends BaseResource<StorageImageDto, StorageImageConditionDto, Long> {

    private final StorageImageService service;

    public StorageImageResource(StorageImageService service) {
        super(service);
        this.service = service;
        this.entityName = "StorageImage";
    }

    @Override
    public void preConditionDto(StorageImageConditionDto queryDto) {

    }

    @ApiOperation(value = "查找明细", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "查询成功", response = String.class)
    @GetMapping(value = "/uuid/{uuid}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<StorageImageDto> getByUuid(@PathVariable("uuid") String uuid) {
        Optional<StorageImageDto> dto = service.getByUuid(uuid);
        return ResponseUtil.wrapOrNotFound(dto);
    }

    @ApiOperation(value = "添加", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "添加成功", response = String.class)
    //    @PreAuthorize("hasPermission(Object target, Object permission)")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @Override
    public ResponseEntity<StorageImageDto> create(@RequestBody StorageImageDto dto){
        // 不需支持该方法
        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "上传图片", httpMethod = "POST")
    @ApiResponse(code = 200, message = "上传图片成功", response = String.class)
//    @PreAuthorize("hasPermission(Object target, Object permission)")
    @PostMapping(value = "/upload")
    @ResponseBody
    public ResponseEntity<StorageImageDto> upload(@RequestParam(value = "file", required = false) MultipartFile multipartFile,
                                                  @RequestParam(value = "tenantId", required = false) Long tenantId,
                                                  @RequestParam(value = "memo", required = false) String memo)
            throws Exception {
        if (ObjectUtils.isEmpty(multipartFile) || multipartFile.isEmpty()) {
            throw new BadRequestAlertException("File cannot be empty",
                    null, "image.empty", entityName);
        }
        StorageStrategy storageService = StorageStrategyFactory.build();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        if(!FileExtension.IMAGE.contains(extension)){
            throw new BadRequestAlertException("Unsupported file extension",
                    null, "image.extension.unsupported", entityName);
        }
        String imageName = uuid + "." + extension;
        String imageRelativePath = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
                + "/" + imageName;
        if (!ObjectUtils.isEmpty(tenantId)) {
            imageRelativePath = tenantId + "/" + imageRelativePath;
        }
        StorageImageDto storageImageDto = new StorageImageDto();
        storageImageDto.setTenantId(tenantId);
        storageImageDto.setUuid(uuid);
        storageImageDto.setFileExtension(extension);
        storageImageDto.setFileName(imageName);
        storageImageDto.setFileSize(multipartFile.getSize());
        // 图片高度、宽度
        BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());
        storageImageDto.setImageWidth(bufferedImage.getWidth());
        storageImageDto.setImageHeight(bufferedImage.getHeight());
        String md5Hex = DigestUtils.md5Hex(multipartFile.getInputStream());
        String sha1Hex = DigestUtils.sha1Hex(multipartFile.getInputStream());
        // 图片MD5、SHA1
        storageImageDto.setFileMd5Hex(md5Hex);
        storageImageDto.setFileSha1Hex(sha1Hex);

        storageService.uploadImage(multipartFile, imageRelativePath, storageImageDto);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityCreationAlert(entityName, storageImageDto.getAlertParam()))
                .body(storageImageDto);
    }

    @ApiOperation(value = "下载图片", httpMethod = "GET")
    @ApiResponse(code = 200, message = "下载图片成功", response = String.class)
    @GetMapping(value = "/download/{uuid}")
    @ResponseBody
    public ResponseEntity<byte[]> download(@PathVariable("uuid") String uuid)
            throws IOException
    {
        Optional<StorageImageDto> optional = service.getByUuid(uuid);
        if(!optional.isPresent()){
            return ResponseEntity.notFound()
                    .headers(HeaderUtil.createFailureAlert("record not found",404,"record.not_found",entityName))
                    .build();
        } else {
            String fileAbsolutePath = optional.get().getFileAbsolutePath();
            if(StringUtils.isBlank(fileAbsolutePath)){
                return ResponseEntity.notFound()
                        .headers(HeaderUtil.createFailureAlert("image path is empty",404,"file_path.empty",entityName))
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

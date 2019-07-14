package com.vinesmario.microservice.server.file.web.rest;

import com.vinesmario.microservice.client.file.dto.FileImageDto;
import com.vinesmario.microservice.client.file.dto.condition.FileImageConditionDto;
import com.vinesmario.microservice.server.common.web.rest.BaseResource;
import com.vinesmario.microservice.server.common.web.rest.errors.BadRequestAlertException;
import com.vinesmario.microservice.server.common.web.rest.util.HeaderUtil;
import com.vinesmario.microservice.server.common.web.rest.util.ResponseUtil;
import com.vinesmario.microservice.server.file.service.FileImageService;
import com.vinesmario.microservice.server.file.storage.AbstractStorageService;
import com.vinesmario.microservice.server.file.storage.StorageServiceFactory;
import com.vinesmario.microservice.server.file.storage.config.StorageProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

/**
 * @author
 * @date
 */

@Api(description = "FileImageCRUD", tags = "FileImageController", basePath = "/file_image")
@Slf4j
@RestController
@RequestMapping("/api/v1/file_image")
public class FileImageResource extends BaseResource<FileImageDto, FileImageConditionDto, Long> {

    @Autowired
    private StorageProperties storageProperties;

    private final FileImageService service;

    public FileImageResource(FileImageService service) {
        super(service);
        this.service = service;
        this.entityName = "FileImage";
    }

    @Override
    public void preConditionDto(FileImageConditionDto queryDto) {

    }

    @ApiOperation(value = "查找明细", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "查询成功", response = String.class)
    @GetMapping(value = "/uuid/{uuid}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<FileImageDto> getByUuid(@PathVariable("uuid") String uuid) {
        Optional<FileImageDto> dto = service.getByUuid(uuid);

//        InputStream in = new FileInputStream(new File(realPath));//将该文件加入到输入流之中
//        byte[] body = null;
//        body = new byte[in.available()];// 返回下一次对此输入流调用的方法可以不受阻塞地从此输入流读取（或跳过）的估计剩余字节数
//        in.read(body);//读入到输入流里面
//
//        fileName = new String(fileName.getBytes("gbk"), "iso8859-1");//防止中文乱码
//        HttpHeaders headers = new HttpHeaders();//设置响应头
//        headers.add("Content-Disposition", "attachment;filename=" + fileName);
//        HttpStatus statusCode = HttpStatus.OK;//设置响应吗
//        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(body, headers, statusCode);
//        return response;

        return ResponseUtil.wrapOrNotFound(dto);
    }

    @ApiOperation(value = "添加", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "添加成功", response = String.class)
//    @PreAuthorize("hasPermission(Object target, Object permission)")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<FileImageDto> create(@RequestParam("image") MultipartFile image,
                                               @RequestParam(value = "name", required = false) String name,
                                               @RequestParam(value = "memo", required = false) String memo)
            throws Exception {
        if (ObjectUtils.isEmpty(image) || image.isEmpty()) {
            throw new BadRequestAlertException("File cannot be empty",
                    null, "file.empty", entityName);
        }
        AbstractStorageService storageService = StorageServiceFactory.build();
        FileImageDto fileImageDto = storageService.uploadImage(image, "");
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityCreationAlert(entityName, fileImageDto.getAlertParam()))
                .body(fileImageDto);
    }
}

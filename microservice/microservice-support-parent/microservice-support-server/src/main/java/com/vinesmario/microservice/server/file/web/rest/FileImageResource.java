package com.vinesmario.microservice.server.file.web.rest;

import com.vinesmario.microservice.client.file.dto.condition.FileImageConditionDto;
import com.vinesmario.microservice.client.file.dto.FileImageDto;
import com.vinesmario.microservice.client.file.web.feign.FileImageClient;
import com.vinesmario.microservice.server.common.web.rest.BaseResource;
import com.vinesmario.microservice.server.file.service.FileImageService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * @date
 */

@Api(description = "FileImageCRUD", tags = "FileImageController", basePath = "/file_image")
@Slf4j
@RestController
@RequestMapping("/api/v1/file_image")
public class FileImageResource extends BaseResource<FileImageDto, FileImageConditionDto, Long>{

    private final FileImageService service;

    public FileImageResource(FileImageService service) {
        super(service);
        this.service = service;
        this.entityName = "FileImage";
    }

    @Override
    public void preConditionDto(FileImageConditionDto queryDto) {

    }

}

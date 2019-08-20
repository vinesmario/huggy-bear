package com.vinesmario.microservice.server.storage.strategy.cloud;

import com.vinesmario.microservice.client.storage.dto.IStorageDTO;
import com.vinesmario.microservice.server.storage.strategy.StorageStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Slf4j
@Lazy
@Service
public abstract class CloudStorageStrategy implements StorageStrategy {

    public <T extends IStorageDTO> void upload(MultipartFile multipartFile, String fileRelativePath, T dto) throws Exception {
        dto.setFileAbsoluteUrl(upload(multipartFile.getInputStream(), fileRelativePath));
    }

    public <T extends IStorageDTO> void upload(InputStream inputStream, String fileRelativePath, T dto) throws Exception {
        dto.setFileAbsoluteUrl(upload(inputStream, fileRelativePath));
    }

    public <T extends IStorageDTO> void upload(byte[] data, String fileRelativePath, T dto) throws Exception {
        dto.setFileAbsoluteUrl(upload(new ByteArrayInputStream(data), fileRelativePath));
    }

}

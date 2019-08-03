package com.vinesmario.microservice.server.storage.strategy.local;

import com.vinesmario.microservice.client.storage.dto.StorageFileDTO;
import com.vinesmario.microservice.server.config.StorageProperties;
import com.vinesmario.microservice.server.storage.strategy.StorageStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
@Lazy
@Service
public class LocalStorageStrategy extends StorageStrategy {

    private final LocalStorageConfig config;

    public LocalStorageStrategy(StorageProperties storageProperties) {
        if (ObjectUtils.isEmpty(storageProperties.getLocal())) {
            log.error("Property 'storage.local' is empty ");
            throw new IllegalArgumentException("Property 'storage.local' is empty ");
        } else if (ObjectUtils.isEmpty(storageProperties.getLocal().getRoot())) {
            log.error("Property 'storage.local.root' is empty ");
            throw new IllegalArgumentException("Property 'storage.local.root' is empty ");
        } else if (ObjectUtils.isEmpty(storageProperties.getLocal().getBucketName())) {
            log.error("Property 'storage.local.bucketName' is empty ");
            throw new IllegalArgumentException("Property 'storage.local.bucketName' is empty ");
        }
        this.config = storageProperties.getLocal();
    }

    @Override
    public boolean isPersistent() {
        return config.isPersistent();
    }

    @Override
    public <T extends StorageFileDTO> void upload(MultipartFile multipartFile, String fileRelativePath, T dto) throws Exception {
        dto.setFileAbsolutePath(upload(multipartFile.getInputStream(), fileRelativePath));
    }

    @Override
    public <T extends StorageFileDTO> void upload(InputStream inputStream, String fileRelativePath, T dto) throws Exception {
        dto.setFileAbsolutePath(upload(inputStream, fileRelativePath));
    }

    @Override
    public <T extends StorageFileDTO> void upload(byte[] data, String fileRelativePath, T dto) throws Exception {
        dto.setFileAbsolutePath(upload(new ByteArrayInputStream(data), fileRelativePath));
    }

    @Override
    public String upload(InputStream inputStream, String fileRelativePath) throws Exception {
        String fileAbsolutePath = config.getRoot() + "/" + config.getBucketName() + "/" + fileRelativePath;
        File file = new File(fileAbsolutePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        OutputStream outputStream = FileUtils.openOutputStream(file);
        IOUtils.copy(inputStream, outputStream);
        outputStream.flush();
        outputStream.close();
        return fileAbsolutePath;
    }

    @Override
    public void deleteObject(String key) throws Exception {

    }

}

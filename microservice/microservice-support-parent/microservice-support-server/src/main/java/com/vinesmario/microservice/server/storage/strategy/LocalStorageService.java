package com.vinesmario.microservice.server.storage.strategy;

import com.vinesmario.microservice.client.storage.dto.StorageFileDto;
import com.vinesmario.microservice.client.storage.dto.StorageImageDto;
import com.vinesmario.microservice.server.storage.config.LocalStorageConfig;
import com.vinesmario.microservice.server.storage.config.StorageProperties;
import com.vinesmario.microservice.server.storage.service.StorageFileService;
import com.vinesmario.microservice.server.storage.service.StorageImageService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

@Lazy
@Service
public class LocalStorageService extends AbstractStorageService {

    @Autowired
    private StorageFileService storageFileService;
    @Autowired
    private StorageImageService storageImageService;

    private final LocalStorageConfig config;

    public LocalStorageService(StorageProperties storageProperties) {
        if (ObjectUtils.isEmpty(storageProperties.getLocal())) {
            throw new IllegalArgumentException("Property 'storage.local' is empty ");
        } else if (ObjectUtils.isEmpty(storageProperties.getLocal().getRoot())) {
            throw new IllegalArgumentException("Property 'storage.local.root' is empty ");
        }
        this.config = storageProperties.getLocal();
    }

    @Override
    public void upload(MultipartFile multipartFile, String fileRelativePath, StorageFileDto storageFileDto) throws Exception {
        storageFileDto.setFileAbsolutePath(upload(multipartFile.getInputStream(), fileRelativePath));
        storageFileDto.setFileRelativeUrl("/api/v1/storage_file/download/{uuid}".replace("{uuid}",storageFileDto.getUuid()));
        storageFileService.create(storageFileDto);
        storageFileDto.setFileAbsolutePath(null);
    }

    @Override
    public void upload(InputStream inputStream, String fileRelativePath, StorageFileDto storageFileDto) throws Exception {
        storageFileDto.setFileAbsolutePath(upload(inputStream, fileRelativePath));
        storageFileDto.setFileRelativeUrl("/api/v1/storage_file/download/{uuid}".replace("{uuid}",storageFileDto.getUuid()));
        storageFileService.create(storageFileDto);
        storageFileDto.setFileAbsolutePath(null);
    }

    @Override
    public void upload(byte[] data, String fileRelativePath, StorageFileDto storageFileDto) throws Exception {
        storageFileDto.setFileAbsolutePath(upload(new ByteArrayInputStream(data), fileRelativePath));
        storageFileDto.setFileRelativeUrl("/api/v1/storage_file/download/{uuid}".replace("{uuid}",storageFileDto.getUuid()));
        storageFileService.create(storageFileDto);
        storageFileDto.setFileAbsolutePath(null);
    }

    @Override
    public void uploadImage(MultipartFile multipartFile, String imageRelativePath, StorageImageDto storageImageDto) throws Exception {
        storageImageDto.setImageAbsolutePath(upload(multipartFile.getInputStream(), imageRelativePath));
        storageImageDto.setImageRelativeUrl("/api/v1/storage_file/download/{uuid}".replace("{uuid}",storageImageDto.getUuid()));
        storageImageService.create(storageImageDto);
        storageImageDto.setImageAbsolutePath(null);
    }

    @Override
    public void uploadImage(InputStream inputStream, String imageRelativePath, StorageImageDto storageImageDto) throws Exception {
        storageImageDto.setImageAbsolutePath(upload(inputStream, imageRelativePath));
        storageImageDto.setImageRelativeUrl("/api/v1/storage_file/download/{uuid}".replace("{uuid}",storageImageDto.getUuid()));
        storageImageService.create(storageImageDto);
        storageImageDto.setImageAbsolutePath(null);
    }

    @Override
    public void uploadImage(byte[] data, String imageRelativePath, StorageImageDto storageImageDto) throws Exception {
        storageImageDto.setImageAbsolutePath(upload(new ByteArrayInputStream(data), imageRelativePath));
        storageImageDto.setImageRelativeUrl("/api/v1/storage_file/download/{uuid}".replace("{uuid}",storageImageDto.getUuid()));
        storageImageService.create(storageImageDto);
        storageImageDto.setImageAbsolutePath(null);
    }

    @Override
    public void deleteObject(String key) throws Exception {

    }

    private String upload(InputStream inputStream, String fileRelativePath) throws Exception {
        if (StringUtils.isNotBlank(config.getBucketName())) {
            fileRelativePath = config.getBucketName() + "/" + fileRelativePath;
        }
        String fileAbsolutePath = config.getRoot() + "/" + fileRelativePath;
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
}

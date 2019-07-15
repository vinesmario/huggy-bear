package com.vinesmario.microservice.server.storage.strategy;

import com.vinesmario.microservice.client.storage.dto.StorageFileDto;
import com.vinesmario.microservice.client.storage.dto.StorageImageDto;
import com.vinesmario.microservice.server.storage.config.StorageProperties;
import com.vinesmario.microservice.server.storage.config.YpyunCloudStorageConfig;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Lazy
@Service
public class YpyunCloudStorageService extends AbstractStorageService {

    private final YpyunCloudStorageConfig config;

    public YpyunCloudStorageService(StorageProperties storageProperties) {
        if (ObjectUtils.isEmpty(storageProperties.getCloud())) {
            throw new IllegalArgumentException("Property 'storage.cloud' is empty ");
        } else if (ObjectUtils.isEmpty(storageProperties.getCloud().getYpyun())) {
            throw new IllegalArgumentException("Property 'storage.cloud.ypyun' is empty ");
        }
        this.config = storageProperties.getCloud().getYpyun();
    }

    @Override
    public void upload(MultipartFile multipartFile, String fileRelativePath, StorageFileDto storageFileDto) throws Exception {

    }

    @Override
    public void upload(InputStream inputStream, String fileRelativePath, StorageFileDto storageFileDto) throws Exception {

    }

    @Override
    public void upload(byte[] data, String fileRelativePath, StorageFileDto storageFileDto) throws Exception {

    }

    @Override
    public void uploadImage(MultipartFile multipartFile, String imageRelativePath, StorageImageDto storageImageDto) throws Exception {

    }

    @Override
    public void uploadImage(InputStream inputStream, String imageRelativePath, StorageImageDto storageImageDto) throws Exception {

    }

    @Override
    public void uploadImage(byte[] data, String imageRelativePath, StorageImageDto storageImageDto) throws Exception {

    }

    @Override
    public void deleteObject(String key) throws Exception {

    }
}

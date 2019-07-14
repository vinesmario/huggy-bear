package com.vinesmario.microservice.server.file.storage;

import com.vinesmario.microservice.client.file.dto.FileImageDto;
import com.vinesmario.microservice.server.file.storage.config.AliyunCloudStorageConfig;
import com.vinesmario.microservice.server.file.storage.config.StorageProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Lazy
@Service
public class AliyunCloudStorageService extends AbstractStorageService {

    private final AliyunCloudStorageConfig config;

    public AliyunCloudStorageService(StorageProperties storageProperties) {
        if (ObjectUtils.isEmpty(storageProperties.getCloud())) {
            throw new IllegalArgumentException("Property 'file.storage.cloud' is empty ");
        } else if (ObjectUtils.isEmpty(storageProperties.getCloud().getAliyun())) {
            throw new IllegalArgumentException("Property 'file.storage.cloud.aliyun' is empty ");
        }
        this.config = storageProperties.getCloud().getAliyun();
    }

    @Override
    public String upload(MultipartFile multipartFile, String path) throws Exception {
        return null;
    }

    @Override
    public String upload(InputStream inputStream, String path) throws Exception {
        return null;
    }

    @Override
    public String upload(byte[] data, String path) throws Exception {
        return null;
    }

    @Override
    public FileImageDto uploadImage(MultipartFile multipartFile, String path) throws Exception {
        return null;
    }

    @Override
    public FileImageDto uploadImage(InputStream inputStream, String path) throws Exception {
        return null;
    }

    @Override
    public FileImageDto uploadImage(byte[] data, String path) throws Exception {
        return null;
    }

    @Override
    public void deleteObject(String key) throws Exception {

    }
}

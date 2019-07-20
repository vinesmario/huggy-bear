package com.vinesmario.microservice.server.storage.strategy.cloud;

import com.vinesmario.microservice.server.config.StorageProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.InputStream;

@Slf4j
@Lazy
@Service
public class YpyunCloudStorageStrategy extends CloudStorageStrategy {

    private final YpyunCloudStorageConfig config;

    public YpyunCloudStorageStrategy(StorageProperties storageProperties) {
        if (ObjectUtils.isEmpty(storageProperties.getCloud())) {
            log.error("Property 'storage.cloud' is empty ");
            throw new IllegalArgumentException("Property 'storage.cloud' is empty ");
        } else if (ObjectUtils.isEmpty(storageProperties.getCloud().getYpyun())) {
            log.error("Property 'storage.cloud.ypyun' is empty ");
            throw new IllegalArgumentException("Property 'storage.cloud.ypyun' is empty ");
        }
        this.config = storageProperties.getCloud().getYpyun();
    }

    @Override
    public boolean isPersistent() {
        return config.isPersistent();
    }

    @Override
    public String upload(InputStream inputStream, String fileRelativePath) throws Exception {
        return null;
    }

    @Override
    public void deleteObject(String key) throws Exception {

    }

}

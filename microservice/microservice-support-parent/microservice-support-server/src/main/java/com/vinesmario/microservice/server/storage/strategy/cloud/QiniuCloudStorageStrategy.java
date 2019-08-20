package com.vinesmario.microservice.server.storage.strategy.cloud;

import com.qiniu.common.Zone;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.vinesmario.microservice.server.config.StorageProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.InputStream;

@Slf4j
@Lazy
@Service
public class QiniuCloudStorageStrategy extends CloudStorageStrategy {

    private final QiniuCloudStorageConfig config;

    public QiniuCloudStorageStrategy(StorageProperties storageProperties) {
        if (ObjectUtils.isEmpty(storageProperties.getCloud())) {
            log.error("Property 'storage.cloud' is empty ");
            throw new IllegalArgumentException("Property 'storage.cloud' is empty ");
        } else if (ObjectUtils.isEmpty(storageProperties.getCloud().getQiniu())) {
            log.error("Property 'storage.cloud.qiniu' is empty ");
            throw new IllegalArgumentException("Property 'storage.cloud.qiniu' is empty ");
        }
        this.config = storageProperties.getCloud().getQiniu();
    }

    public boolean isPersistent() {
        return config.isPersistent();
    }

    public String upload(InputStream inputStream, String fileRelativePath) throws Exception {
        if (StringUtils.isNotBlank(config.getNameSpace())) {
            fileRelativePath = config.getNameSpace() + "/" + fileRelativePath;
        }
        UploadManager uploadManager = new UploadManager(new Configuration(Zone.autoZone()));
        String token = Auth.create(config.getAccessKey(), config.getSecretKey()).uploadToken(config.getBucketName());
        uploadManager.put(inputStream, fileRelativePath, token, null, null);
        String fileAbsoluteUrl = "http://" + config.getDomain() + "/" + fileRelativePath;
        return fileAbsoluteUrl;
    }

    public void deleteObject(String key) throws Exception {

    }

}

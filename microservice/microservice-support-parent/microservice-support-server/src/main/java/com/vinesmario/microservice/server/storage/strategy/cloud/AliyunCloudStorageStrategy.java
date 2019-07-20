package com.vinesmario.microservice.server.storage.strategy.cloud;

import com.aliyun.oss.OSSClient;
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
public class AliyunCloudStorageStrategy extends CloudStorageStrategy {

    private final AliyunCloudStorageConfig config;

    public AliyunCloudStorageStrategy(StorageProperties storageProperties) {
        if (ObjectUtils.isEmpty(storageProperties.getCloud())) {
            log.error("Property 'storage.cloud' is empty ");
            throw new IllegalArgumentException("Property 'storage.cloud' is empty ");
        } else if (ObjectUtils.isEmpty(storageProperties.getCloud().getAliyun())) {
            log.error("Property 'storage.cloud.aliyun' is empty ");
            throw new IllegalArgumentException("Property 'storage.cloud.aliyun' is empty ");
        }
        this.config = storageProperties.getCloud().getAliyun();
    }

    @Override
    public boolean isPersistent() {
        return config.isPersistent();
    }

    @Override
    public String upload(InputStream inputStream, String fileRelativePath){
        if (StringUtils.isNotBlank(config.getNameSpace())) {
            fileRelativePath = config.getNameSpace() + "/" + fileRelativePath;
        }
        OSSClient client = new OSSClient(config.getEndPoint(), config.getAccessKeyId(), config.getAccessKeySecret());
        client.putObject(config.getBucketName(), fileRelativePath, inputStream);
        String fileAbsoluteUrl = "http://" + config.getDomain() + "/" + fileRelativePath;
        return fileAbsoluteUrl;
    }

    @Override
    public void deleteObject(String key) throws Exception {

    }

}

package com.vinesmario.microservice.server.storage.config;

import lombok.Data;

@Data
public class QiniuCloudStorageConfig {
    /**
     * 七牛绑定的域名
     */
    private String domain;
    /**
     * 七牛路径前缀
     */
    private String prefix;
    /**
     * 七牛ACCESS_KEY
     */
    private String accessKey;
    /**
     * 七牛SECRET_KEY
     */
    private String secretKey;
    /**
     * 七牛存储空间名
     */
    private String bucketName;
}

package com.vinesmario.microservice.server.storage.strategy.cloud;

import lombok.Data;

/**
 * 七牛云
 */
@Data
public class QiniuCloudStorageConfig {
    /**
     * 七牛云绑定的域名
     */
    private String domain;
    /**
     * 七牛云ACCESS_KEY
     */
    private String accessKey;
    /**
     * 七牛云SECRET_KEY
     */
    private String secretKey;
    /**
     * 七牛云存储空间名
     */
    private String bucketName;
    /**
     * 七牛云访问url命名空间
     */
    private String nameSpace;
    /**
     * 持久化文件记录
     */
    private boolean persistent = true;

}

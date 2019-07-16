package com.vinesmario.microservice.server.storage.strategy.cloud;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 阿里云
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AliyunCloudStorageConfig {
    /**
     * 阿里云绑定的域名
     */
    private String domain;
    /**
     * 阿里云EndPoint
     */
    private String endPoint;
    /**
     * 阿里云AccessKeyId
     */
    private String accessKeyId;
    /**
     * 阿里云AccessKeySecret
     */
    private String accessKeySecret;
    /**
     * 阿里云存储空间名
     */
    private String bucketName;
    /**
     * 阿里云访问url命名空间
     */
    private String nameSpace;

}

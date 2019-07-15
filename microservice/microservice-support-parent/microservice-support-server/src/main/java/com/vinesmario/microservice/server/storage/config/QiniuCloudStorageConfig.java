package com.vinesmario.microservice.server.storage.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QiniuCloudStorageConfig {
    /**
     * 七牛绑定的域名
     */
    private String domain;
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
    /**
     * 七牛访问url命名空间
     */
    private String nameSpace;
}

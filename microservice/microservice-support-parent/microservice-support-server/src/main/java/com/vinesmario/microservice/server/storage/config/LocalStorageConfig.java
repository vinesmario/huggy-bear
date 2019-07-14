package com.vinesmario.microservice.server.storage.config;

import lombok.Data;

@Data
public class LocalStorageConfig {
    /**
     * 根目录
     */
    private String root;
    /**
     * 存储空间名
     */
    private String bucketName;

}

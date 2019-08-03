package com.vinesmario.microservice.server.storage.strategy.local;

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
    /**
     * 持久化文件记录
     */
    private boolean persistent = true;
    /**
     * 临时文件目录
     */
    private String tmp;
    
}

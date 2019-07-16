package com.vinesmario.microservice.server.storage.strategy.local;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

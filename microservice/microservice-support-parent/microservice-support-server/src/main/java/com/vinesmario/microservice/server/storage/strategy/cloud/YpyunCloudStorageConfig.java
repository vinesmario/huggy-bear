package com.vinesmario.microservice.server.storage.strategy.cloud;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 又拍云
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class YpyunCloudStorageConfig {
    /**
     * 又拍云绑定的域名
     */
    private String domain;
    /**
     * 持久化文件记录
     */
    private boolean persistent = true;

}

package com.vinesmario.microservice.server.storage.strategy.cloud;

import lombok.Data;

/**
 * 又拍云
 */
@Data
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

package com.vinesmario.microservice.server.storage.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YpyunCloudStorageConfig {
    /**
     * 又拍绑定的域名
     */
    private String domain;
}

package com.vinesmario.microservice.server.file.storage.config;

import lombok.Data;

@Data
public class YpyunCloudStorageConfig {
    private String url;
    private String key;
    private String secret;
}

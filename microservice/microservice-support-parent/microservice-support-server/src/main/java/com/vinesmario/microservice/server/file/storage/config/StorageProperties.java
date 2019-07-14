package com.vinesmario.microservice.server.file.storage.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("file.storage")
public class StorageProperties {

    private LocalStorageConfig local;
    private CloudStorageConfig cloud;

}

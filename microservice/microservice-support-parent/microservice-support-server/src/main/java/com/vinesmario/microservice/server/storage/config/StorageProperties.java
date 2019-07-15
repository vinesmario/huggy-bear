package com.vinesmario.microservice.server.storage.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("storage")
public class StorageProperties {

    private LocalStorageConfig local;
    private CloudStorageConfig cloud;

}

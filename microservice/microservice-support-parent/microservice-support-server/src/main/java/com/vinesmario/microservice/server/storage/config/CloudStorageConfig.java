package com.vinesmario.microservice.server.storage.config;

import lombok.Data;

@Data
public class CloudStorageConfig {

    private String strategy;
    private AliyunCloudStorageConfig aliyun;
    private QiniuCloudStorageConfig qiniu;
    private YpyunCloudStorageConfig ypyun;

}
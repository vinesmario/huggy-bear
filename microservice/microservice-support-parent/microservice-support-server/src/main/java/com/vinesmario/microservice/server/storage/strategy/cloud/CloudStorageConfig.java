package com.vinesmario.microservice.server.storage.strategy.cloud;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CloudStorageConfig {

    private String strategy;
    private AliyunCloudStorageConfig aliyun;
    private QiniuCloudStorageConfig qiniu;
    private YpyunCloudStorageConfig ypyun;

}

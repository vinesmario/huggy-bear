package com.vinesmario.microservice.server.file.storage.config;

import com.vinesmario.microservice.server.file.storage.AliyunCloudStorageService;
import com.vinesmario.microservice.server.file.storage.QiniuCloudStorageService;
import com.vinesmario.microservice.server.file.storage.YpyunCloudStorageService;
import org.apache.commons.lang3.StringUtils;

public enum CloudStorageStrategy {
    YPYUN("YPYUN", YpyunCloudStorageService.class),
    QINIU("QINIU", QiniuCloudStorageService.class),
    ALIYUN("ALITUN", AliyunCloudStorageService.class);

    private String type;
    private Class clazz;

    public String getType() {
        return type;
    }

    public Class getClazz() {
        return clazz;
    }

    CloudStorageStrategy(String type, Class clazz) {
        this.type = type;
        this.clazz = clazz;
    }

    public static Class getClassByType(String type) {
        if (StringUtils.isBlank(type)) {
            throw new RuntimeException("Strategy type is empty");
        } else if (YPYUN.getType().equals(type)) {
            return YPYUN.clazz;
        } else if (QINIU.getType().equals(type)) {
            return QINIU.clazz;
        } else if (ALIYUN.getType().equals(type)) {
            return ALIYUN.clazz;
        } else {
            throw new RuntimeException("Unsupported Strategy type: " + type);
        }
    }
}

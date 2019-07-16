package com.vinesmario.microservice.server.storage.strategy;

import org.apache.commons.lang3.StringUtils;

public enum CloudStorageStrategyEnum {

    YPYUN("YPYUN", YpyunCloudStorageStrategy.class),
    QINIU("QINIU", QiniuCloudStorageStrategy.class),
    ALIYUN("ALITUN", AliyunCloudStorageStrategy.class);

    private String strategy;
    private Class clazz;

    public String getStrategy() {
        return strategy;
    }

    public Class getClazz() {
        return clazz;
    }

    CloudStorageStrategyEnum(String strategy, Class clazz) {
        this.strategy = strategy;
        this.clazz = clazz;
    }

    public static Class getClassByStrategy(String strategy) {
        if (StringUtils.isBlank(strategy)) {
            throw new RuntimeException("strategy is empty");
        } else if (YPYUN.getStrategy().equals(strategy)) {
            return YPYUN.clazz;
        } else if (QINIU.getStrategy().equals(strategy)) {
            return QINIU.clazz;
        } else if (ALIYUN.getStrategy().equals(strategy)) {
            return ALIYUN.clazz;
        } else {
            throw new RuntimeException("Unsupported strategy: " + strategy);
        }
    }
}

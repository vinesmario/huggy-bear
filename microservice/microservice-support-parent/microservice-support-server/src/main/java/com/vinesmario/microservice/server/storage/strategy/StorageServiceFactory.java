package com.vinesmario.microservice.server.storage.strategy;

import com.vinesmario.microservice.server.common.util.SpringContextUtil;
import com.vinesmario.microservice.server.storage.config.CloudStorageStrategy;
import com.vinesmario.microservice.server.storage.config.StorageProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

public class StorageServiceFactory {

    public static AbstractStorageService build() {
        StorageProperties storageProperties = SpringContextUtil.getBean(StorageProperties.class);
        if (!ObjectUtils.isEmpty(storageProperties.getLocal())
                && StringUtils.isNotBlank(storageProperties.getLocal().getRoot())) {
            // 优先读取local配置
            return SpringContextUtil.getBean(LocalStorageService.class);
        } else {
            // 无cloud配置，则要求完成local配置
            if (ObjectUtils.isEmpty(storageProperties.getCloud())) {
                if (ObjectUtils.isEmpty(storageProperties.getLocal())) {
                    throw new IllegalArgumentException("Property 'storage.local' is empty ");
                } else if (StringUtils.isBlank(storageProperties.getLocal().getRoot())) {
                    throw new IllegalArgumentException("Property 'storage.local.root' is empty ");
                }
            }

            // 有cloud配置，则获取云存储策略
            if (StringUtils.isBlank(storageProperties.getCloud().getStrategy())) {
                throw new IllegalArgumentException("Property 'storage.cloud.strategy' is empty ");
            } else {
                return buildCloud(storageProperties.getCloud().getStrategy());
            }
        }
    }

    public static AbstractStorageService buildCloud(String strategy) {
        Class storageClass = CloudStorageStrategy.getClassByType(strategy);
        return (AbstractStorageService) SpringContextUtil.getBean(storageClass);
    }

}

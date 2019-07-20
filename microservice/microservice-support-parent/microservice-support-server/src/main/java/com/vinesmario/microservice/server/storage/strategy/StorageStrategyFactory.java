package com.vinesmario.microservice.server.storage.strategy;

import com.vinesmario.microservice.server.common.util.SpringContextUtil;
import com.vinesmario.microservice.server.config.StorageProperties;
import com.vinesmario.microservice.server.storage.strategy.cloud.CloudStorageStrategyEnum;
import com.vinesmario.microservice.server.storage.strategy.local.LocalStorageStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

@Slf4j
public class StorageStrategyFactory {

    public static StorageStrategy build() {
        StorageProperties storageProperties = SpringContextUtil.getBean(StorageProperties.class);
        if (!ObjectUtils.isEmpty(storageProperties.getLocal())
                && StringUtils.isNotBlank(storageProperties.getLocal().getRoot())) {
            // 优先读取local配置
            return SpringContextUtil.getBean(LocalStorageStrategy.class);
        } else {
            // 无cloud配置，则要求完成local配置
            if (ObjectUtils.isEmpty(storageProperties.getCloud())) {
                if (ObjectUtils.isEmpty(storageProperties.getLocal())) {
                    log.error("Property 'storage.local' is empty ");
                    throw new IllegalArgumentException("Property 'storage.local' is empty ");
                } else if (StringUtils.isBlank(storageProperties.getLocal().getRoot())) {
                    log.error("Property 'storage.local.root' is empty ");
                    throw new IllegalArgumentException("Property 'storage.local.root' is empty ");
                }
            }

            // 有cloud配置，则获取云存储策略
            if (StringUtils.isBlank(storageProperties.getCloud().getStrategy())) {
                log.error("Property 'storage.cloud.strategy' is empty ");
                throw new IllegalArgumentException("Property 'storage.cloud.strategy' is empty ");
            } else {
                return buildCloud(storageProperties.getCloud().getStrategy());
            }
        }
    }

    public static StorageStrategy buildCloud(String strategy) {
        Class strategyClass = CloudStorageStrategyEnum.getClassByStrategy(strategy);
        return (StorageStrategy) SpringContextUtil.getBean(strategyClass);
    }

}

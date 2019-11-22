package com.vinesmario.microservice.client.device.dto;

/**
 * 终端
 * 创建设备同时可以选择创建终端
 * 心跳监测等配置在终端中维护
 */
public class TerminalDTO {

    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 设备ID
     */
    private Long deviceId;

}

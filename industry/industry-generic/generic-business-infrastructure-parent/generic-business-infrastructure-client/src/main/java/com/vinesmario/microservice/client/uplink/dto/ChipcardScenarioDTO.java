package com.vinesmario.microservice.client.uplink.dto;

/**
 * 芯片卡识别业务场景
 */
public class ChipcardScenarioDTO {
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 设备ID
     */
    private Long deviceId;
    /**
     * 卡ID
     */
    private Long chipcardId;
    /**
     * 卡号，唯一编号
     */
    private String serialNumber;
    /**
     *
     */
    private Long userId;
}

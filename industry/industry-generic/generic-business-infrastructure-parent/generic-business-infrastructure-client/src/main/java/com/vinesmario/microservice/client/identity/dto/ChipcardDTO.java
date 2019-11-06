package com.vinesmario.microservice.client.identity.dto;

/**
 * 芯片卡
 * 由租户发放
 */
public class ChipcardDTO {
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     *
     */
    private Long userId;
    /**
     * 卡号，唯一编号
     */
    private String serialNumber;

}

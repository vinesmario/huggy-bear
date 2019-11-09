package com.vinesmario.microservice.client.downlink.dto;

/**
 * 芯片卡数据补丁下行
 *
 */
public class ChipcardDownlinkDTO {
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 设备ID
     */
    private Long deviceId;
    /**
     * 人员ID
     */
    private Long userId;
    /**
     * 芯片卡ID
     */
    private Long chipcardId;
    /**
     * 数据库基本操作
     * C-create；D-delete
     */
    private String operation;
    /**
     * 下发成功 0-否；1-是
     */
    private Integer downlinkStatus;
}

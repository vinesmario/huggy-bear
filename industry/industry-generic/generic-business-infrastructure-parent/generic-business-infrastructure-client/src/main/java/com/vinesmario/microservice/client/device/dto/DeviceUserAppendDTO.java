package com.vinesmario.microservice.client.device.dto;

/**
 * 设备与人员关系更新记录
 */
public class DeviceUserAppendDTO {
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
     * 数据库基本操作
     * C-create；D-delete
     */
    private String operation;
    /**
     * 人脸数据生成补丁 0-否；1-是
     */
    private Integer facePatch;
    /**
     * 芯片卡数据生成补丁 0-否；1-是
     */
    private Integer chipcardPatch;
}

package com.vinesmario.microservice.client.downlink.dto;

/**
 * 设备与芯片卡关系数据补丁
 *
 */
public class DeviceChipcardPatchDTO
{
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 设备ID
     */
    private Long deviceId;
    /**
     * 用户组ID
     */
    private Long userGroupId;
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

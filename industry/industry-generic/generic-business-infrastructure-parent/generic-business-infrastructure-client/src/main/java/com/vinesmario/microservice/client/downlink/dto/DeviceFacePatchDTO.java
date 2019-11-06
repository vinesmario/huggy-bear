package com.vinesmario.microservice.client.downlink.dto;

/**
 * 设备与人脸关系数据补丁
 */
public class DeviceFacePatchDTO
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
     * 人脸ID
     */
    private Long faceId;
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

package com.vinesmario.microservice.client.downlink.dto;

/**
 * 人脸数据下发补丁
 */
public class FaceDownlinkDTO {
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

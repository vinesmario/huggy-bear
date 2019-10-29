package com.vinesmario.microservice.client.liveness.detection.dto;

/**
 * 设备人脸更新记录
 */
public class CameraFaceAppendDTO {
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 设备ID
     */
    private Long cameraId;
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
     * 下行状态 0-否；1-是
     */
    private Integer downlinkStatus;
}

package com.vinesmario.microservice.client.detection.dto;

/**
 * 设备与人脸关系
 */
public class CameraFaceDTO {
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 设备ID
     * 支持人脸作为信息载体的设备
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
}

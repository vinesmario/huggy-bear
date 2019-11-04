package com.vinesmario.microservice.client.detection.dto;

/**
 * 设备与群组关系
 */
public class CameraFaceGroupDTO {
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

}

package com.vinesmario.microservice.client.liveness.detection.dto;

import java.time.LocalDateTime;

/**
 * 人脸识别
 */
public class FaceRecognitionDTO {
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 设备ID
     */
    private Long cameraId;
    /**
     * 设备名称
     */
    private Long cameraName;
    /**
     * 人员ID
     */
    private Long userId;
    /**
     * userId、serialNumber为空，则为“陌生人”
     */
    private Long userName;
    /**
     * 学工号
     */
    private String serialNumber;
    /**
     * 人脸ID
     */
    private Long faceId;
    /**
     * 抓拍人脸访问绝对url
     */
    private String faceAbsoluteUrl;
    /**
     * 抓拍人脸访问相对url
     */
    private String faceRelativeUrl;
    /**
     * 识别时间
     */
    private LocalDateTime recognitionTime;
    /**
     * 相似度0~100
     */
    private Integer similatity;
    /**
     * 用途：1-考勤签到；2-限制通行。
     */
    private Integer purpose;
    /**
     * 下行状态 0-否；1-是
     */
    private Integer downlinkStatus;
}

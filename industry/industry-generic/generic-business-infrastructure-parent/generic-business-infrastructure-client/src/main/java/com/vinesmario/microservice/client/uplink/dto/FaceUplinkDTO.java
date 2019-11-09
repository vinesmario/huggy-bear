package com.vinesmario.microservice.client.uplink.dto;

/**
 * 人脸识别业务数据上行
 */
public class FaceUplinkDTO {
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 设备ID
     */
    private Long deviceId;
    /**
     * 人脸ID
     */
    private Long faceId;
    /**
     *
     */
    private Long userId;
    /**
     * 应用场景：1-工作打卡；2-会议签到；3-宿舍考勤；4-支付
     */
    private Integer scenario;
}

package com.vinesmario.microservice.client.uplink.dto;

/**
 * 二维码识别业务数据上行
 */
public class QRCodeUplinkDTO {
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 设备ID
     */
    private Long deviceId;
    /**
     * 唯一标识
     */
    private Long qrcodeId;
    /**
     * 唯一标识
     */
    private String uuid;
    /**
     *
     */
    private Long userId;
    /**
     * 应用场景：1-工作打卡；2-会议签到；3-宿舍考勤；4-支付
     */
    private Integer scenario;
}

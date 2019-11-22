package com.vinesmario.microservice.client.uplink.dto;

/**
 * 指纹识别业务数据上行
 */
public class FingerprintUplinkDTO {
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 设备ID
     */
    private Long deviceId;
    /**
     *
     */
    private Long fingerprintId;
    /**
     * 人员ID
     */
    private Long personId;
    /**
     * 人员名称
     */
    private Long personName;
    /**
     * 应用场景：1-工作打卡；2-会议签到；3-宿舍考勤；4-支付
     */
    private Integer scenario;
}

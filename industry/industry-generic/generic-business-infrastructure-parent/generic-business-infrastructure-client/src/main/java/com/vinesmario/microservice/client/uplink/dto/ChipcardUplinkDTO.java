package com.vinesmario.microservice.client.uplink.dto;

/**
 * 芯片卡识别业务数据上行
 */
public class ChipcardUplinkDTO {
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 设备ID
     */
    private Long deviceId;
    /**
     * 卡ID
     */
    private Long chipcardId;
    /**
     * 卡号，唯一编号
     */
    private String serialNumber;
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

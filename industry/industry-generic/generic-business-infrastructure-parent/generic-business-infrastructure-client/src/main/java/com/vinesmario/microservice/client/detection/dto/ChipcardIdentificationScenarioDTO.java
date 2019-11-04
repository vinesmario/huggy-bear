package com.vinesmario.microservice.client.detection.dto;

import java.time.LocalDateTime;

/**
 * 芯片卡识别场景
 */
public class ChipcardIdentificationScenarioDTO {
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 设备ID
     */
    private Long deviceId;
    /**
     * 设备名称
     */
    private Long deviceName;
    /**
     * 应用场景：1-工作打卡；2-会议签到；3-宿舍考勤；4-支付
     */
    private Integer scenario;
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
     * 芯片卡ID
     */
    private Long chipcardId;
    /**
     * 芯片卡唯一标识码
     */
    private String chipcardSerialNumber;
    /**
     * 识别时间
     */
    private LocalDateTime identificationTime;
    /**
     * 下行状态 0-否；1-是
     */
    private Integer downlinkStatus;
}

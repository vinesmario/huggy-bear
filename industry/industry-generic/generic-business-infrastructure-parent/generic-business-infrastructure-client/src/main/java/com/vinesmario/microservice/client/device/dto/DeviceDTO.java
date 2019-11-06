package com.vinesmario.microservice.client.device.dto;

/**
 * 设备
 */
public class DeviceDTO
{
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 名称
     */
    private String name;
    /**
     * 编号
     */
    private String serialNumber;
    /**
     * 品牌
     */
    private String brand;
    /**
     * 型号
     */
    private String modelNumber;
    /**
     * 应用场景：1-工作打卡；2-会议签到；3-宿舍考勤；4-支付
     */
    private Integer scenario;
    /**
     * 身份载体
     * 0-无；1-人脸；2-芯片卡；4-二维码；
     * 3-人脸+芯片卡；5-人脸+二维码
     * 6-芯片卡+二维码；7-人脸+芯片卡+二维码
     */
    private Integer identityCarrier;
    /**
     * 房间（空间）ID
     * 应用场景为1-工作打卡；2-会议签到；3-宿舍考勤时 必选
     */
    private Long roomId;
    /**
     * 方向 I-进；O-出
     * 应用场景为1-工作打卡；2-会议签到；3-宿舍考勤时 建议选
     */
    private String direction;
    /**
     * 录像机ID
     */
    private Long recorderId;

}

package com.vinesmario.microservice.client.movable.dto;

/**
 * 网络摄像机
 */
public class CameraDTO {
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
     * 用途：1-考勤签到；2-限制通行
     */
    private String purpose;
    /**
     * 建筑物ID，非必需项
     */
    private Long buildingId;
    /**
     * 房间（空间）ID
     */
    private Long roomId;
    /**
     * 通道ID，考勤用途，则通道ID不能为空
     */
    private Long passageId;
    /**
     * 录像机ID
     */
    private Long recorderId;

}

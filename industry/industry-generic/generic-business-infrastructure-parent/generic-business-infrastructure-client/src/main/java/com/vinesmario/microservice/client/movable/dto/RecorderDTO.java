package com.vinesmario.microservice.client.movable.dto;

/**
 * 网络录像机
 */
public class RecorderDTO {
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

}

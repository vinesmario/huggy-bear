package com.vinesmario.microservice.client.immovable.dto;

/**
 * 通道
 */
public class PassageDTO {
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 建筑物ID，非必需项
     */
    private Long buildingId;
    /**
     * 房间（空间）ID
     */
    private String roomId;
    /**
     * 名称
     */
    private String name;
    /**
     * 通道方向 I-进；O-出
     */
    private String direction;

}

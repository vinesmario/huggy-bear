package com.vinesmario.microservice.client.immovable.dto;

/**
 * 会议室
 */
public class MeetingRoomDTO {
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 建筑物ID，非必需项
     */
    private Long buildingId;
    /**
     * 场所ID
     */
    private Long facilityId;
    /**
     * 名称
     */
    private String name;
    /**
     * 编码
     */
    private String code;
}

package com.vinesmario.microservice.client.immovable.dto;

import lombok.Data;

/**
 * 教室
 */
@Data
public class ClassRoomDTO {
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
    /**
     * 可容纳人数/可容纳货物数
     */
    private Integer capacity;
    /**
     * 容量单位
     */
    private String capacityUnit;

}

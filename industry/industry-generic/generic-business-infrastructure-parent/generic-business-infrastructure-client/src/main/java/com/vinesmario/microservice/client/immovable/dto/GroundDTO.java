package com.vinesmario.microservice.client.immovable.dto;

/**
 * 开阔地面
 */
public class GroundDTO {
    /**
     * 用途：泊车、集会
     */
    private Integer purpose;
    /**
     * 泊车 可容纳车数
     * 集会 可容纳人数
     */
    private Integer capacity;
    /**
     * 容量单位
     */
    private String capacityUnit;
}

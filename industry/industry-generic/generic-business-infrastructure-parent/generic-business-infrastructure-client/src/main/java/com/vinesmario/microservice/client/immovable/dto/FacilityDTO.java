package com.vinesmario.microservice.client.immovable.dto;

/**
 * 场所
 */
public class FacilityDTO {
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 名称
     */
    private String name;
    /**
     * 用途：1-办公；2-会议；3-教学；4-居住；5-储物；9-其他。
     */
    private Integer purpose;
}

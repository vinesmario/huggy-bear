package com.vinesmario.microservice.client.structure;

/**
 * 用户组
 */
public class UserGroupDTO {
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 名称
     */
    private String name;
    /**
     * 用途 1-device；2-rotation
     */
    private Byte purpose;
}

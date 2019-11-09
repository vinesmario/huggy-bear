package com.vinesmario.microservice.client.structure;

/**
 * 用户标签关系
 */
public class UserLabelDTO {
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 用户组ID，人脸库ID
     */
    private Long labelId;
    /**
     * 人员ID
     */
    private Long userId;
}

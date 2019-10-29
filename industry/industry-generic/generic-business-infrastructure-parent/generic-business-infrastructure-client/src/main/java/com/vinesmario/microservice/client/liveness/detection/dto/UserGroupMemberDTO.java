package com.vinesmario.microservice.client.liveness.detection.dto;

/**
 * 用户组成员
 */
public class UserGroupMemberDTO {
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 用户组ID，人脸库ID
     */
    private Long userGroupId;
    /**
     * 人员ID
     */
    private Long userId;
    /**
     * 人脸ID
     */
    private Long faceId;
}

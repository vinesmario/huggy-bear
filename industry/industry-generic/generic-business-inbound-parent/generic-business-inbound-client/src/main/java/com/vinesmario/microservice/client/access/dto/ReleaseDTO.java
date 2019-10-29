package com.vinesmario.microservice.client.access.dto;

import java.time.LocalDateTime;

/**
 * 放行
 */
public class ReleaseDTO {
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 人员ID
     */
    private Long userId;
    /**
     * 人员名称
     */
    private Long userName;
    /**
     * 房间（空间）ID
     */
    private Long roomId;
    /**
     * 房间（空间）名称
     */
    private Long roomName;
    /**
     * 通道方向 I-进；O-出
     */
    private String direction;
    /**
     * 放行时间
     */
    private LocalDateTime signTime;
    /**
     * 通道ID
     */
    private Long passageId;
    /**
     * 通道名称
     */
    private String passageName;
    /**
     * 设备ID
     */
    private Long equipmentId;
    /**
     * 设备名称
     */
    private String equipmentName;
}

package com.vinesmario.microservice.client.attendance.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 考勤
 */
public class AttendanceDTO {

    private Long tenantId;
    private Long oauthUserId;
    private Long roomId;
    private Long scheduleId;
    /**
     * 考勤日期
     */
    private LocalDate scheduleDate;
    /**
     * 签名时间
     */
    private LocalDateTime signTime;
    /**
     * 签名设备
     */
    private String signDevice;
    /**
     * 方向 1-到达；2-离开
     */
    private Integer direction;

}

package com.vinesmario.microservice.client.attendance.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 考勤日报
 */
public class RotationClockDailyDTO {
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 考核日期
     */
    private LocalDate checkDate;
    /**
     * 人员ID
     */
    private Long userId;
    /**
     * 人员名称
     */
    private Long userName;
    /**
     * 常班时长（小时）
     */
    private BigDecimal rotationDuration;
    /**
     * 出勤时长（小时）
     */
    private BigDecimal presenceDuration;
    /**
     * 迟到时长（小时）
     */
    private BigDecimal lateDuration;
    /**
     * 早退时长（小时）
     */
    private BigDecimal earlyDuration;
    /**
     * 旷工时长（小时）
     */
    private BigDecimal absenceDuration;
    /**
     * 休假时长（小时）
     */
    private BigDecimal leaveDuration;
    /**
     * 加班时长（小时）
     */
    private BigDecimal overtimeDuration;
}

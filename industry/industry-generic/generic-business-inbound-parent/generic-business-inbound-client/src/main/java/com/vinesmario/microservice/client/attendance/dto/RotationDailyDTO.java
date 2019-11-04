package com.vinesmario.microservice.client.attendance.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 考勤日报
 */
public class RotationDailyDTO {
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 考勤日期
     */
    private LocalDate attendanceDate;
    /**
     * 人员ID
     */
    private Long userId;
    /**
     * 人员名称
     */
    private Long userName;
    /**
     * 状态
     * 0-待处理；
     * 1-正常；
     * 2-迟到/早退；
     * 3-旷工；
     * 4-请假（事假）；41-年假；42-婚假；43-产假；44-丧假；45-陪产假；46-工伤；47-病假；
     * 5-调休；
     * 6-出差；
     * 99-其他
     */
    private Integer attendanceStatus;
    /**
     * 应到时长（小时）
     */
    private BigDecimal rotationHour;
    /**
     * 出勤时长（小时）
     */
    private BigDecimal presentHour;
    /**
     * 缺勤时长（小时）
     */
    private BigDecimal absenceHour;
    /**
     * 加班时长（小时）
     */
    private BigDecimal overtimeHour;

}

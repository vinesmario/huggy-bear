package com.vinesmario.microservice.client.attendance.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 加班人员计划
 */
public class RotationOvertimeDTO {
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 考勤日期
     */
    private LocalDate attendanceDate;
    /**
     * 开始时间
     */
    private LocalTime beginTime;
    /**
     * 结束时间
     */
    private LocalTime endTime;
    /**
     * 次日结束 0-否；1-是
     */
    private Integer endMorrow;
    /**
     * 房间（空间）ID，可默认取值
     */
    private Long roomId;
    /**
     * 房间（空间）名称
     */
    private String roomName;
    /**
     * 人员ID
     */
    private Long userId;
    /**
     * 人员名称
     */
    private String userName;
    /**
     * 理由
     */
    private String reason;

}

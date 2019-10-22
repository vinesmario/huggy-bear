package com.vinesmario.microservice.client.attendance.dto;

import java.time.LocalDate;

/**
 * 考勤计划 时间+地点+人员
 */
public class AttendanceScheduleDTO {

    private Long tenantId;
    private Long crontabId;
    /**
     * 起始日期
     */
    private LocalDate beginDate;
    /**
     * 结束日期
     */
    private LocalDate endDate;
    private Long roomId;
    private Long oauthUserId;

}

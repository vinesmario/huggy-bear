package com.vinesmario.microservice.client.attendance.service.dto;

import java.time.LocalDate;

/**
 * 时间表 时间+地点+人员
 */
public class TimetableDTO {

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

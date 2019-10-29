package com.vinesmario.microservice.client.production.service.dto;

import java.time.LocalDate;

/**
 * 学期
 */
public class SemesterDTO {
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 起始日期
     */
    private LocalDate beginDate;
    /**
     * 结束日期
     */
    private LocalDate endDate;
    /**
     * 持续周数
     */
    private Integer durationWeek;

}

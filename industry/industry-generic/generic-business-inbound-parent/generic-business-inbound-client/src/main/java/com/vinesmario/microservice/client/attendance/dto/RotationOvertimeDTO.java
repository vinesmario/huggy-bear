package com.vinesmario.microservice.client.attendance.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
     * 开始日期
     */
    private LocalDate beginDate;
    /**
     * 结束日期
     */
    private LocalDate endDate;
    /**
     * 场所ID
     */
    private Long facilityId;
    /**
     * 场所名称
     */
    private String facilityName;
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
    /**
     * 状态 0-待审批；1-审批中；2-通过；3-驳回；4-撤销
     */
    private Integer status;
    /**
     * 当前审批记录ID
     */
    private Long approvalId;

}

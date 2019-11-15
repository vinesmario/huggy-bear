package com.vinesmario.microservice.client.attendance.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 请假、调休人员计划
 */
public class RotationLeaveDTO {
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 开始时间
     */
    private LocalDateTime beginTime;
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    /**
     * 时长（小时）
     */
    private BigDecimal duration;
    /**
     * 人员
     */
    private Long userId;
    /**
     * 人员名称
     */
    private String userName;
    /**
     * 类型 1-年假；2-事假；3-病假；4-调休假；5-婚假；6-产假；7-陪产假；9-其他
     */
    private Integer leaveType;
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
    private Long reviewlId;

}

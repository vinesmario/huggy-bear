package com.vinesmario.microservice.client.attendance.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 按月活动打卡
 * 例如，打卡上班、下班
 */
public class RotationClockDTO {
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 计划ID
     */
    private Long planId;
    /**
     * 计划名称
     */
    private Long planName;
    /**
     * 计划来源 1-正常班；2-加班；
     */
    private Integer planSourceType;
    /**
     * 严格的  0-否；1-是
     */
    private Integer strict;
    /**
     * 提前（分钟）量
     */
    private Integer minutesAheadPlan;
    /**
     * 延后（分钟）量
     */
    private Integer minutesBehindPlan;
    /**
     * 考核日期
     */
    private LocalDate checkDate;
    /**
     * 开始时间
     */
    private LocalDateTime beginTime;
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    /**
     * 人员ID
     */
    private Long userId;
    /**
     * 人员名称
     */
    private Long userName;
    /**
     * 场所ID
     */
    private Long facilityId;
    /**
     * 场所名称
     */
    private String facilityName;
    /**
     * 上班打卡时间
     */
    private LocalDateTime clockInTime;
    /**
     * 打卡状态
     * 0-待打卡；
     * 1-正常打卡；
     * 2-迟到/早退；
     * 9-补卡；
     */
    private Integer clockInStatus;
    /**
     * 上班打卡方式 1-人脸识别；2-门禁卡；3-指纹机
     */
    private Integer clockInType;
    /**
     * 上班打卡记录ID
     */
    private Long clockInRecordId;
    /**
     * 下班打卡时间
     */
    private LocalDateTime clockOutTime;
    /**
     * 下班打卡状态
     * 0-待打卡；
     * 1-正常打卡；
     * 2-迟到/早退；
     * 9-补卡；
     */
    private Integer clockOutStatus;
    /**
     * 下班打卡方式 1-人脸识别；2-门禁卡；3-指纹机
     */
    private Integer clockOutType;
    /**
     * 下班打卡记录ID
     */
    private Long clockOutRecordId;
    /**
     * 休假类型 1-年假；2-事假；3-病假；4-调休假；5-婚假；6-产假；7-陪产假；9-其他
     */
    private Byte leaveType;
    /**
     * 休假开始时间
     */
    private LocalDateTime leaveBeginTime;
    /**
     * 休假结束时间
     */
    private LocalDateTime leaveEndTime;

}

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
     * 房间（空间）ID
     */
    private Long roomId;
    /**
     * 房间（空间）名称
     */
    private Long roomName;
    /**
     * 上班打卡时间
     */
    private LocalDateTime clockInTime;
    /**
     * 打卡状态
     * 0-待打卡；
     * 1-正常打卡；
     * 2-迟到/早退；
     * 3-旷工；
     * 4-请假（事假）；41-年假；42-婚假；43-产假；44-丧假；45-陪产假；46-工伤；47-病假；
     * 5-调休；
     * 6-出差；
     * 7-补卡；
     * 99-其他
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
     * 3-旷工；
     * 4-请假（事假）；41-年假；42-婚假；43-产假；44-丧假；45-陪产假；46-工伤；47-病假；
     * 5-调休；
     * 6-出差；
     * 7-补卡；
     * 99-其他
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

}

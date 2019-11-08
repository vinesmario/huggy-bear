package com.vinesmario.microservice.client.attendance.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 按次活动签到
 * 例如会议
 */
public class SessionSignDTO {
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
     * 计划来源 1-会议；
     */
    private Integer planSourceType;
    /**
     * 严格的  0-否；1-是
     * 默认为1-是
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
     * 签到时间
     */
    private LocalDateTime signInTime;
    /**
     * 签到状态
     * 0-待签；
     * 1-正常；
     * 2-迟到/早退；
     * 3-缺席；
     * 4-请假
     * 5-代席
     * 99-其他
     */
    private Integer signInStatus;
    /**
     * 签到方式 1-人脸识别；2-门禁卡；3-指纹机
     */
    private Integer signInType;
    /**
     * 签到记录ID
     */
    private Long signInRecordId;
    /**
     * 签退时间
     */
    private LocalDateTime signOutTime;
    /**
     * 签退状态
     * 0-待签；
     * 1-正常；
     * 2-迟到/早退；
     * 3-缺席；
     * 4-请假（事假）；41-年假；42-婚假；43-产假；44-丧假；45-陪产假；46-工伤；47-病假；
     * 5-调休；
     * 6-出差；
     * 7-补卡；
     * 99-其他
     */
    private Integer signOutStatus;
    /**
     * 签退方式 1-人脸识别；2-门禁卡；3-指纹机
     */
    private Integer signOutType;
    /**
     * 签退记录
     */
    private Long signOutRecordId;

}

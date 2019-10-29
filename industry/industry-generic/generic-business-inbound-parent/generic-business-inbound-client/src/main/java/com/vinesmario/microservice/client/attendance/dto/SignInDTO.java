package com.vinesmario.microservice.client.attendance.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 考勤签到
 */
public class SignInDTO {
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
     * 计划来源 1-轮岗；2-加班；3-会议；
     */
    private Integer planSourceType;
    /**
     * 严格的  0-否；1-是
     */
    private Integer strict;
    /**
     * 考勤日期
     */
    private LocalDate attendanceDate;
    /**
     * 考勤时间
     */
    private LocalTime attendanceTime;
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
     * 通道方向 I-进；O-出
     */
    private String direction;
    /**
     * 签名时间
     */
    private LocalDateTime signTime;
    /**
     * 签名方式 1-人脸识别；2-门禁卡
     */
    private Integer signType;
    /**
     * 签名通道ID
     */
    private Long passageId;
    /**
     * 签名通道名称
     */
    private String passageName;
    /**
     * 签名设备ID
     */
    private Long equipmentId;
    /**
     * 签名设备名称
     */
    private String equipmentName;
    /**
     * 签名状态
     * 0-待签名；
     * 1-正常；
     * 2-迟到/早退；
     * 3-旷工；
     * 4-请假（事假）；41-年假；42-婚假；43-产假；44-丧假；45-陪产假；46-工伤；47-病假；
     * 5-调休；
     * 6-出差；
     * 7-补卡；
     * 99-其他
     */
    private Integer signStatus;

}

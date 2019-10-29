package com.vinesmario.microservice.client.attendance.dto;

import java.time.LocalDate;

/**
 * 轮岗计划更新记录
 */
public class RotationPlanAppandDTO {
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 排期ID
     */
    private Long crontabId;
    /**
     * 排期名称
     */
    private Long crontabName;
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
     * 每月的第{day}日执行
     * day=*表示每天执行
     * 1~31
     */
    private Integer day;
    /**
     * 每年的第{month}月执行
     * month=*表示每月执行
     * 1~12
     */
    private Integer month;
    /**
     * 每周的第{week}天（星期一至星期日）执行
     * week=*表示不限制
     * 星期日~星期六
     * 1~7 1=SUN
     */
    private Integer week;
    /**
     * 隔周执行 0-否；1-是
     */
    private Integer alternateWeek;
    /**
     * 隔周执行的起始周 0-无；1-单周；2-双周
     */
    private Integer parityWeek;
    /**
     * 排除节假日 0-否；1-是
     */
    private Integer excludeHoliday;
    /**
     * 起始日期
     */
    private LocalDate beginDate;
    /**
     * 结束日期
     */
    private LocalDate endDate;
    /**
     * 房间（区域）ID
     */
    private Long roomId;
    /**
     * 房间（区域）名称
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
     * 数据库基本操作
     * C-create；D-delete
     */
    private String operation;

}

package com.vinesmario.microservice.client.attendance.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 排班群组计划 时间+地点+用户组
 */
public class RotationGroupDTO {
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 名称
     */
    private Long name;
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
     * 每周的第{week}天（星期日至星期六）执行
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
     * 隔周执行的单双周 0-无；1-单周；2-双周
     */
    private Integer parityWeek;
    /**
     * 排除节假日 0-否；1-是
     */
    private Integer excludeHoliday;
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
     * 开始日期
     */
    private LocalDate beginDate;
    /**
     * 结束日期
     */
    private LocalDate endDate;
    /**
     * 房间（空间）ID
     */
    private Long roomId;
    /**
     * 房间（空间）名称
     */
    private String roomName;
    /**
     * 用户组ID
     */
    private Long userGroupId;
    /**
     * 用户组名称
     */
    private String userGroupName;
}

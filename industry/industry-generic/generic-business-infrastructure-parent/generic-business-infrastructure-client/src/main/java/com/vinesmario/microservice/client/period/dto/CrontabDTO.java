package com.vinesmario.microservice.client.period.dto;

import java.time.LocalTime;

/**
 * 排程
 */
public class CrontabDTO {

    private Long tenantId;
    /**
     * 起始时间
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
     */
    private String day;
    /**
     * 每年的第{month}月执行
     * month=*表示每月执行
     */
    private String month;
    /**
     * 每周的第{week}天（星期一至星期日）执行
     * week=*表示不限制
     */
    private String week;
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

}

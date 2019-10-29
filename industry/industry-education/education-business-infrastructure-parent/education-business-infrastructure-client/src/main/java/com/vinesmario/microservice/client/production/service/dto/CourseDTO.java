package com.vinesmario.microservice.client.production.service.dto;

import java.time.LocalDate;

/**
 * 课程
 */
public class CourseDTO {
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 名称
     */
    private String name;
    /**
     * 代码
     */
    private String code;
    /**
     * 排期ID
     */
    private Long crontabId;
    /**
     *
     */
    private Long crontabName;
    /**
     * 每周的第{week}天（星期一至星期日）执行
     * week=*表示不限制
     * 星期一~星期日
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
    /**
     * 学期ID
     */
    private Long semesterId;
    /**
     * 起始日期
     */
    private LocalDate beginDate;
    /**
     * 结束日期
     */
    private LocalDate endDate;
    /**
     * 起始周
     */
    private Integer beginWeek;
    /**
     * 结束周
     */
    private Integer endWeek;
    /**
     * 科目ID
     */
    private Long subjectId;
    private Long roomId;
    private Long roomName;
    private Long roomCode;
    private Long teacherId;
    private Long teacherName;
}

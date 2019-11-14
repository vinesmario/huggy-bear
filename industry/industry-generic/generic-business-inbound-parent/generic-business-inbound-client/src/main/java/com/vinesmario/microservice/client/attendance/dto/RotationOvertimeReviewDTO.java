package com.vinesmario.microservice.client.attendance.dto;

/**
 * 加班审批
 * 单向链表结点
 */
public class RotationOvertimeReviewDTO {

    /**
     * 加班申请ID
     */
    private Long overtimeId;
    /**
     * 审批人ID
     */
    private Long userId;
    /**
     * 审批人名称
     */
    private String userName;
    /**
     * 审批者顺序，由低到高
     */
    private Byte userSort;
    /**
     * 0-不同意；1-同意；
     */
    private Boolean approved;
    /**
     * 评论
     */
    private String remark;
    /**
     * 下一审批记录ID
     */
    private Long nextId;

}

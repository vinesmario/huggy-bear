package com.vinesmario.microservice.client.attendance.dto;

/**
 * 加班审批
 * 单向链表结点
 */
public class RotationOvertimeApprovalDTO {

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
     * 理由
     */
    private String reason;
    /**
     * 0-不同意；1-同意；
     */
    private Integer status;
    /**
     * 下一审批记录ID
     */
    private Long nextId;

}

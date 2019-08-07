package com.vinesmario.microservice.client.enterprise.dto;

import com.vinesmario.microservice.client.common.dto.BaseDTO;

/**
 * 小组成员，小组与职工关系
 */
public class TeamMemberDTO extends BaseDTO<Long> {

    private Long teamId;
    private Long staffId;
    /**
     * 任务职责
     */
    private String duty;

    @Override
    public String getAlertParam() {
        return duty;
    }
}

package com.vinesmario.microservice.client.structure.dto;

import com.vinesmario.microservice.client.common.dto.BaseDTO;

/**
 * 小组成员，小组与职工关系
 */
public class TeamMemberDTO extends BaseDTO<Long> {

    private Long teamId;
    private Long userAccountId;

    @Override
    public String getAlertParam() {
        return null;
    }
}

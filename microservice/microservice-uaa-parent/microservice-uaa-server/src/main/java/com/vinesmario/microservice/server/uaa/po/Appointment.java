package com.vinesmario.microservice.server.uaa.po;

import com.vinesmario.microservice.server.common.po.BasePO;
import lombok.Data;

/**
 * 任职
 * 用户OauthUser和岗位Position是多对多关系
 * 被任职的用户OauthUser获得职工（B端用户）身份
 */
@Data
public class Appointment extends BasePO<Long> {

    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 岗位ID
     */
    private Long positionId;
    /**
     * 用户ID
     */
    private Long oauthUserId;

}

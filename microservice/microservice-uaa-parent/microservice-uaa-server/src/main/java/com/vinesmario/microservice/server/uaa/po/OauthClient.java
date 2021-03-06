package com.vinesmario.microservice.server.uaa.po;

import com.vinesmario.microservice.server.common.po.BasePO;
import lombok.Data;

/**
 *
 */
@Data
public class OauthClient extends BasePO<Long> {

    private String clientId;
    private String clientSecret;
    /**
     * 集合，逗号分隔
     */
    private String resourceIds;
    /**
     * 集合，逗号分隔
     */
    private String scopes;
    /**
     * 集合，逗号分隔
     */
    private String authorizedGrantTypes;
    /**
     * 集合，逗号分隔
     */
    private String webServerRedirectUris;
    /**
     * 集合，逗号分隔
     */
    private String authorities;
    /**
     * 单位 秒
     */
    private Integer accessTokenValidity;
    /**
     * 单位 秒
     */
    private Integer refreshTokenValidity;
    /**
     * map
     * StringSplitUtils
     */
    private String additionalInformation;
    private Boolean autoApprove;

}

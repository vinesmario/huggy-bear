package com.vinesmario.microservice.server.uaa.po;

import com.vinesmario.microservice.server.common.po.BasePO;
import lombok.Data;

/**
 * 许可
 * 职权Authority和资源Resource是多对对关系
 */
@Data
public class Permission extends BasePO<Long> {

    /**
     * 职权ID
     */
    private Long authorityId;
    /**
     * 资源ID
     */
    private Long resourceId;

}

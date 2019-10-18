package com.vinesmario.microservice.server.uaa.po;

import com.vinesmario.microservice.server.common.po.BasePO;
import lombok.Data;

/**
 * 职权，资源Resource组
 */
@Data
public class Authority extends BasePO<Long> {

    /**
     * 职权名称
     */
    private String name;

}

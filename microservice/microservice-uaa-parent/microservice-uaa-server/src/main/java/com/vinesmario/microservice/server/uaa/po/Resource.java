package com.vinesmario.microservice.server.uaa.po;

import com.vinesmario.microservice.server.common.po.TreePO;
import lombok.Data;

/**
 * 资源
 */
@Data
public class Resource extends TreePO<Long> {

    /**
     * 类型
     */
    private Integer type;
    /**
     * 许可
     */
    private String permission;

}

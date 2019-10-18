package com.vinesmario.microservice.server.uaa.po;

import com.vinesmario.microservice.server.common.po.TreePO;

/**
 * 职业目录
 * 《职业分类大典2015年》
 */
public class ProfessionCatalog extends TreePO<Long> {

    /**
     * 名称
     */
    private String name;
    /**
     * 代码
     */
    private String code;
    /**
     * 等级
     */
    private String level;

}

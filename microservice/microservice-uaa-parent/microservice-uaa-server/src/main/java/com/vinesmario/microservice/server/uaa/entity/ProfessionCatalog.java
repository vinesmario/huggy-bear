package com.vinesmario.microservice.server.uaa.entity;

import com.vinesmario.microservice.server.common.entity.TreeEntity;

/**
 * 职业目录
 * 《职业分类大典2015年》
 */
public class ProfessionCatalog extends TreeEntity<Long> {

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

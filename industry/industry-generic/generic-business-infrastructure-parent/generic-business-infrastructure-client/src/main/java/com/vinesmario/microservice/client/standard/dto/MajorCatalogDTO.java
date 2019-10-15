package com.vinesmario.microservice.client.standard.dto;

import com.vinesmario.microservice.client.common.dto.TreeDTO;

/**
 * 专业目录
 * 学位授予和人才培养目录（2011年）二级学科由学位授予单位自主设置与调整
 * 普通高等学校本科专业目录（2012年）
 * 《专业目录》分为学科门类、专业类和专业三级，其代码分别用两位、四位和六位数字表示。
 * 《专业目录》实行分类管理。《专业目录》十年修订一次；基本专业五年调整一次，特设专业每年动态调整。
 * 普通高等学校高等职业教育（专科）专业目录（2015年）
 * 中等职业学校专业目录（2010年）
 */
public class MajorCatalogDTO extends TreeDTO<MajorCatalogDTO, Long> {

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

    @Override
    public String getAlertParam() {
        return name;
    }
}

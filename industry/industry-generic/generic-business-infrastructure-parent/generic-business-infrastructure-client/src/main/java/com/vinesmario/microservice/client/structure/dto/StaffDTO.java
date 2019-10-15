package com.vinesmario.microservice.client.structure.dto;

import com.vinesmario.microservice.client.common.dto.BaseDTO;

/**
 * 全体职工
 */
public class StaffDTO extends BaseDTO<Long> {

    private String name;
    /**
     * 用户登录账号ID，创建用户登录账号后，返回用户登录账号ID，再保存入库。
     */
    private Long userAccountId;

    @Override
    public String getAlertParam() {
        return name;
    }
}

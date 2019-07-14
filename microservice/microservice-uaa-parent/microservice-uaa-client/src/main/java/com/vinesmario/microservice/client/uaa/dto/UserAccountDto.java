package com.vinesmario.microservice.client.uaa.dto;

import com.vinesmario.microservice.client.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author
 * @date
 */
@ApiModel(value = "UserAccountDto", description = "UserAccountDto")
@Data
public class UserAccountDto extends BaseDto<Long> {

    /**
     * 用户名
     */
    private String username;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 是否激活 0-否；1-是
     */
    private Byte activated;

    /**
     * 角色列表
     */
    private List<AuthorityDto> authorityList;

    @Override
    public String getAlertParam() {
        return username;
    }
}
package com.vinesmario.microservice.server.uaa.entity;

import com.vinesmario.microservice.server.common.entity.BaseEntity;
import lombok.Data;

/**
 * @author
 * @date
 */

@Data
public class UserAccount extends BaseEntity<Long> {

    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 用户名，唯一行约束，不可修改
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
     * 是否激活
     */
    private Byte activated;

}
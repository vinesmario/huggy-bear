package com.vinesmario.microservice.server.uaa.po;

import com.vinesmario.microservice.server.common.po.BasePO;
import lombok.Data;

/**
 * 用户账号
 * 新增或注册，自动获取消费者（C端用户）身份
 * 新增或注册的用户，被任职后才会获得职工（B端用户）身份
 * 被任职的用户需要绑定才可登陆使用B端前端页面。
 */

@Data
public class OauthUser extends BasePO<Long> {

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
     * 编号
     */
    private String serialNumber;
    /**
     * 是否激活
     */
    private Byte activated;

}
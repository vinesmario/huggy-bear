package com.vinesmario.microservice.client.uaa.dto.condition;

import com.vinesmario.microservice.client.common.dto.condition.ConditionDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author
 * @date
 */
@ApiModel(value = "OauthUserConditionDTO", description = "OauthUserConditionDTO")
@Data
public class OauthUserConditionDTO implements ConditionDTO {

    /**
     * ID
     */
    private Long id;
    /**
     * ID为空，true表示查询记录为空
     */
    private boolean idIsNull = false;
    /**
     * ID列表
     */
    private List<Long> ids;
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
     * 分页参数
     */
    private Integer pageNumber;
    private Integer pageSize;
    private String[] sort;

}
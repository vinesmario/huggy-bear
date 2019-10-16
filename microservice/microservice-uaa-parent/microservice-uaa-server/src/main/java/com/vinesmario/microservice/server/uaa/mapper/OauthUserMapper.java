package com.vinesmario.microservice.server.uaa.mapper;

import com.vinesmario.microservice.server.common.persistence.mybatis.mapper.CrudMapper;
import com.vinesmario.microservice.server.uaa.entity.OauthUser;

/**
 * @author
 * @date
 */

public interface OauthUserMapper extends CrudMapper<OauthUser, Long>{

    OauthUser selectByUsername(String username);

    OauthUser selectByMobile(String mobile);

    OauthUser selectByEmail(String email);
}

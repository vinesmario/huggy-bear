package com.vinesmario.microservice.server.uaa.mapper;

import com.vinesmario.microservice.server.common.persistence.mybatis.mapper.CrudMapper;
import com.vinesmario.microservice.server.uaa.entity.UserAccount;

/**
 * @author
 * @date
 */

public interface UserAccountMapper extends CrudMapper<UserAccount, Long>{

    UserAccount selectByUsername(String username);

    UserAccount selectByMobile(String mobile);

    UserAccount selectByEmail(String email);
}

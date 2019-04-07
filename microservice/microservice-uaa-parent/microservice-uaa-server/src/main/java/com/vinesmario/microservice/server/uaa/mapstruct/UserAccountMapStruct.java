package com.vinesmario.microservice.server.uaa.mapstruct;

import com.vinesmario.microservice.server.common.mapstruct.BaseMapStruct;
import com.vinesmario.microservice.client.uaa.dto.UserAccountDto;
import com.vinesmario.microservice.server.uaa.entity.UserAccount;
import org.mapstruct.Mapper;

/**
 * @author
 * @date
 */

@Mapper(componentModel = "spring")
public interface UserAccountMapStruct extends BaseMapStruct<UserAccount, UserAccountDto> {

}

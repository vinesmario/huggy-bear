package com.vinesmario.microservice.server.uaa.mapstruct;

import com.vinesmario.microservice.server.common.mapstruct.BaseMapStruct;
import com.vinesmario.microservice.client.uaa.dto.OauthUserDTO;
import com.vinesmario.microservice.server.uaa.entity.OauthUser;
import org.mapstruct.Mapper;

/**
 * @author
 * @date
 */

@Mapper(componentModel = "spring")
public interface OauthUserMapStruct extends BaseMapStruct<OauthUser, OauthUserDTO> {

}

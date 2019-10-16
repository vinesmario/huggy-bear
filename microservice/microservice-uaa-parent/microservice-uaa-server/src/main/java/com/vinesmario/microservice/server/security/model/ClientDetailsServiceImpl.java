package com.vinesmario.microservice.server.security.model;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.client.http.StringSplitUtils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
public class ClientDetailsServiceImpl implements ClientDetailsService {

    // TODO database table oauth_client_details
//    @Autowired
//    private ClientService clientService;

    @Override
    public ClientDetails loadClientByClientId(final String clientId) throws ClientRegistrationException {
        SecurityHelper.setLocalClientId(clientId);

        // TODO get ClientDetails from database
        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId("internal");
        clientDetails.setClientSecret("internal");
        clientDetails.setResourceIds(Arrays.asList(StringUtils.split("resource-uaa,resource-support,resource-schedule",",")));
        clientDetails.setScope(Arrays.asList(StringUtils.split("web-app,web-app",",")));
        clientDetails.setAccessTokenValiditySeconds(3600);
        clientDetails.setRefreshTokenValiditySeconds(604800);
        return clientDetails;
    }

}

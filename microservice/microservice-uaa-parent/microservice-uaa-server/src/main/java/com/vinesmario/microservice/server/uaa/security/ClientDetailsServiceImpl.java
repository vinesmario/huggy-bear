package com.vinesmario.microservice.server.uaa.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ClientDetailsServiceImpl implements ClientDetailsService {

    // TODO table oauth_client_details
//    @Autowired
//    private ClientService clientService;

    @Override
    public ClientDetails loadClientByClientId(final String clientId) throws ClientRegistrationException {
        // TODO get ClientDetails from database
        return null;
    }

}

package com.vinesmario.microservice.server.security.model;

import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for Spring Security.
 */
@Slf4j
public final class SecurityHelper {

    protected static final ThreadLocal<String> LOCAL_CLIENTID = new ThreadLocal();

    private SecurityHelper() {
    }

    protected static void setLocalClientId(String clientId) {
        LOCAL_CLIENTID.set(clientId);
    }

    public static String getLocalClientId() {
        return LOCAL_CLIENTID.get();
    }

    public static void clearClientId() {
        LOCAL_CLIENTID.remove();
    }


}

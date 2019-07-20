package com.vinesmario.microservice.server.security;

import org.springframework.security.core.AuthenticationException;

/**
 * This exception is thrown in case of a user who was not assigned roles trying to authenticate.
 */
public class UserNotAssignedRolesException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

    public UserNotAssignedRolesException(String message) {
        super(message);
    }

    public UserNotAssignedRolesException(String message, Throwable t) {
        super(message, t);
    }
}

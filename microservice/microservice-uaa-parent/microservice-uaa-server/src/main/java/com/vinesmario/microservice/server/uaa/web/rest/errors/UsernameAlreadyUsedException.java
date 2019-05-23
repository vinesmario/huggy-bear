package com.vinesmario.microservice.server.uaa.web.rest.errors;

import com.vinesmario.microservice.server.common.web.rest.errors.BadRequestAlertException;
import com.vinesmario.microservice.server.common.web.rest.errors.ErrorConstants;

import java.net.URI;

public class UsernameAlreadyUsedException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public UsernameAlreadyUsedException(String entityName) {
        super(ErrorConstants.USERNAME_ALREADY_USED_TYPE, "Username is already in use!", null, "username.used", entityName);
    }

    public UsernameAlreadyUsedException(String defaultMessage, Integer errorCode, String errorKey, String entityName) {
        super(defaultMessage, errorCode, errorKey, entityName);
    }

    public UsernameAlreadyUsedException(URI type, String defaultMessage, Integer errorCode, String errorKey, String entityName) {
        super(type, defaultMessage, errorCode, errorKey, entityName);
    }
}

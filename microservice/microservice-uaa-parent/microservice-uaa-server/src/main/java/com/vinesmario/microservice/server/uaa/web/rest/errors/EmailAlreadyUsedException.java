package com.vinesmario.microservice.server.uaa.web.rest.errors;

import com.vinesmario.microservice.server.common.web.rest.errors.BadRequestAlertException;
import com.vinesmario.microservice.server.common.web.rest.errors.ErrorConstants;

import java.net.URI;

public class EmailAlreadyUsedException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public EmailAlreadyUsedException(String entityName) {
        super(ErrorConstants.EMAIL_ALREADY_USED_TYPE, "Email is already in use!", null, "email.exists", entityName);
    }

    public EmailAlreadyUsedException(String defaultMessage, Integer errorCode, String errorKey, String entityName) {
        super(defaultMessage, errorCode, errorKey, entityName);
    }

    public EmailAlreadyUsedException(URI type, String defaultMessage, Integer errorCode, String errorKey, String entityName) {
        super(type, defaultMessage, errorCode, errorKey, entityName);
    }

}

package com.vinesmario.microservice.server.uaa.web.rest.errors;

import com.vinesmario.microservice.server.common.web.rest.errors.BadRequestAlertException;
import com.vinesmario.microservice.server.common.web.rest.errors.ErrorConstants;

import java.net.URI;

public class MobileAlreadyUsedException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public MobileAlreadyUsedException(String entityName) {
        super(ErrorConstants.MOBILE_ALREADY_USED_TYPE, "Mobile is already in use!", null, "mobile.used", entityName);
    }

    public MobileAlreadyUsedException(String defaultMessage, Integer errorCode, String errorKey, String entityName) {
        super(defaultMessage, errorCode, errorKey, entityName);
    }

    public MobileAlreadyUsedException(URI type, String defaultMessage, Integer errorCode, String errorKey, String entityName) {
        super(type, defaultMessage, errorCode, errorKey, entityName);
    }

}

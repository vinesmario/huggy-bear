package com.vinesmario.microservice.server.uaa.web.rest.errors;

import com.vinesmario.microservice.server.common.web.rest.errors.BadRequestAlertException;
import com.vinesmario.microservice.server.common.web.rest.errors.ErrorConstants;

import java.net.URI;

public class MobileAlreadyUsedException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public MobileAlreadyUsedException() {
        super(ErrorConstants.MOBILE_ALREADY_USED_TYPE, "Mobile is already in use!", "UserAccount", "emailexists");
    }

    public MobileAlreadyUsedException(String defaultMessage, String entityName, String errorKey) {
        super(defaultMessage, entityName, errorKey);
    }

    public MobileAlreadyUsedException(URI type, String defaultMessage, String entityName, String errorKey) {
        super(type, defaultMessage, entityName, errorKey);
    }

}

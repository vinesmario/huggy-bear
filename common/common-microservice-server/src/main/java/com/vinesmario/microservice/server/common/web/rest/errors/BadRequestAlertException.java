package com.vinesmario.microservice.server.common.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class BadRequestAlertException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    private final String entityName;

    private final Integer errorCode;

    private final String errorKey;

    public BadRequestAlertException(String defaultMessage, Integer errorCode, String errorKey, String entityName) {
        this(ErrorConstants.DEFAULT_TYPE, defaultMessage, errorCode, errorKey, entityName);
    }

    public BadRequestAlertException(URI type, String defaultMessage, Integer errorCode, String errorKey, String entityName) {
        super(type, defaultMessage, Status.BAD_REQUEST, null, null, null,
                getAlertParameters(errorCode, errorKey, entityName));
        this.entityName = entityName;
        this.errorCode = errorCode;
        this.errorKey = errorKey;
    }

    public String getEntityName() {
        return entityName;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorKey() {
        return errorKey;
    }

    private static Map<String, Object> getAlertParameters(Integer errorCode, String errorKey, String entityName) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("code", errorCode);
        parameters.put("message", "error." + errorKey);
        parameters.put("params", entityName);
        return parameters;
    }
}

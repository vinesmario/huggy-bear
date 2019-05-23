package com.vinesmario.microservice.server.common.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;

import java.util.HashMap;
import java.util.Map;

import static org.zalando.problem.Status.BAD_REQUEST;

/**
 * Custom, parameterized exception, which can be translated on the client side.
 * For example:
 *
 * <pre>
 * throw new CustomParameterizedException(&quot;myCustomError&quot;, &quot;hello&quot;, &quot;world&quot;);
 * </pre>
 * <p>
 * Can be translated with:
 *
 * <pre>
 * "error.myCustomError" :  "The server says {{param0}} to {{param1}}"
 * </pre>
 */
public class CustomParameterizedException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    private static final String PARAM = "param";

    public CustomParameterizedException(Integer errorCode, String message, String... params) {
        this(errorCode, message, toParamMap(params));
    }

    public CustomParameterizedException(Integer errorCode, String message, Map<String, Object> paramMap) {
        super(ErrorConstants.PARAMETERIZED_TYPE, "Parameterized Exception", BAD_REQUEST, null, null, null,
                toProblemParameters(errorCode, message, paramMap));
    }

    public static Map<String, Object> toParamMap(String... params) {
        Map<String, Object> paramMap = new HashMap<>();
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                paramMap.put(PARAM + i, params[i]);
            }
        }
        return paramMap;
    }

    public static Map<String, Object> toProblemParameters(Integer errorCode, String message, Map<String, Object> paramMap) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("code", errorCode);
        parameters.put("message", message);
        parameters.put("params", paramMap);
        return parameters;
    }
}

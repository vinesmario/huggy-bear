package com.vinesmario.microservice.server.common.web.rest.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * Utility class for HTTP headers creation.
 */
@Slf4j
public final class HeaderUtil {

    private static final String APPLICATION_NAME = "server";

    private HeaderUtil() {
    }

    public static HttpHeaders createPage(Page page) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-" + APPLICATION_NAME + "-page-number", String.valueOf(page.getNumber()));//当前页数
        headers.add("X-" + APPLICATION_NAME + "-page-size", String.valueOf(page.getSize()));//每页大小
        headers.add("X-" + APPLICATION_NAME + "-total-page", String.valueOf(page.getTotalPages()));//总页数
        headers.add("X-" + APPLICATION_NAME + "-total-count", String.valueOf(page.getTotalElements()));//总记录数
        headers.add("X-" + APPLICATION_NAME + "-sort", page.getSort().toString());//排序
        return headers;
    }

    public static HttpHeaders createAlert(String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-" + APPLICATION_NAME + "-alert-message", message);
        headers.add("X-" + APPLICATION_NAME + "-alert-params", param);
        return headers;
    }

    public static HttpHeaders createEntityCreationAlert(String entityName, String param) {
        return createAlert(APPLICATION_NAME + "." + entityName + ".created", param);
    }

    public static HttpHeaders createEntityUpdateAlert(String entityName, String param) {
        return createAlert(APPLICATION_NAME + "." + entityName + ".updated", param);
    }

    public static HttpHeaders createEntityDeletionAlert(String entityName, String param) {
        return createAlert(APPLICATION_NAME + "." + entityName + ".deleted", param);
    }

    public static HttpHeaders createEntityDeletionCollectionAlert(String entityName) {
        return createAlert(APPLICATION_NAME + "." + entityName + ".deleted.collection", null);
    }

    public static HttpHeaders createFailureAlert(String defaultMessage, Integer errorCode, String errorKey, String entityName) {
        log.error("Entity processing failed, {}", defaultMessage);
        HttpHeaders headers = new HttpHeaders();
        headers.add("errorCode", String.valueOf(errorCode));
        headers.add("errorMessage", defaultMessage);
        headers.add("X-" + APPLICATION_NAME + "-error-message", "error." + errorKey);
        headers.add("X-" + APPLICATION_NAME + "-error-params", entityName);
        return headers;
    }

    public static HttpHeaders createDownload(String fileName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return headers;
    }

}

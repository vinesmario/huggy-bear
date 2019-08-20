package com.vinesmario.microservice.client.storage.dto;

public interface IStorageDTO {

    Long getTenantId();

    String getUuid();

    String getFileExtension();

    String getFileName();

    void setFileAbsolutePath(String upload);

    String getFileAbsolutePath();

    String getFileAbsoluteUrl();

    void setFileAbsoluteUrl(String upload);

    String getFileRelativeUrl();

    Long getFileSize();

    String getFileMd5Hex();

    String getFileSha1Hex();

}

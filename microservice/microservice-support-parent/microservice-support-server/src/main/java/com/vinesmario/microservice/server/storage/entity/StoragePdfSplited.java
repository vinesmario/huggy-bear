package com.vinesmario.microservice.server.storage.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author
 * @date
 */
@Data
@Table(name = "storage_pdf_splited")
public class StoragePdfSplited extends StorageFile {

    /**
     * 来源ID
     */
    @Column(name = "ORIGIN_ID")
    private Long originId;
    /**
     * 来源UUID
     */
    @Column(name = "ORIGIN_UUID")
    private String originUuid;
    /**
     * 页码，从第1页开始
     */
    @Column(name = "PAGINATION")
    private Integer pagination;

}
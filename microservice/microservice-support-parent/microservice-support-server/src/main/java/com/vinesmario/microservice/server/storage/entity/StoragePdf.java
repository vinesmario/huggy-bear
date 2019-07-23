package com.vinesmario.microservice.server.storage.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author
 * @date
 */
@Data
@Table(name = "storage_pdf")
public class StoragePdf extends StorageFile {

    /**
     * 页数
     */
    @Column(name = "PDF_PAGE_COUNT")
    private Integer pdfPageCount;
    /**
     * 已拆分,0-否;1-是
     */
    @Column(name = "SPLITED")
    private Byte splited;

}
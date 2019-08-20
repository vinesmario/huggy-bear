package com.vinesmario.microservice.server.storage.entity;

import com.vinesmario.microservice.server.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author
 * @date
 */
@Data
@Table(name = "storage_excel")
public class StorageExcel extends BaseEntity<Long> {

    /**
     *
     */
    @Column(name = "TENANT_ID")
    private Long tenantId;
    /**
     * UUID
     */
    @Column(name = "UUID")
    private String uuid;
    /**
     * 文件扩展名
     */
    @Column(name = "FILE_EXTENSION")
    private String fileExtension;
    /**
     * 文件名称
     */
    @Column(name = "FILE_NAME")
    private String fileName;
    /**
     * 文件存储绝对路径
     */
    @Column(name = "FILE_ABSOLUTE_PATH")
    private String fileAbsolutePath;
    /**
     * 文件访问绝对url
     */
    @Column(name = "FILE_ABSOLUTE_URL")
    private String fileAbsoluteUrl;
    /**
     * 文件访问相对url
     */
    @Column(name = "FILE_RELATIVE_URL")
    private String fileRelativeUrl;
    /**
     * 文件大小，单位byte(B)
     */
    @Column(name = "FILE_SIZE")
    private Long fileSize;
    /**
     * 文件MD5算法哈希值
     */
    @Column(name = "FILE_MD5_HEX")
    private String fileMd5Hex;
    /**
     * 文件SHA1算法哈希值
     */
    @Column(name = "FILE_SHA1_HEX")
    private String fileSha1Hex;

}
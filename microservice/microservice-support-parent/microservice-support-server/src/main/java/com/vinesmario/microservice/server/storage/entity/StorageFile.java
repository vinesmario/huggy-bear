package com.vinesmario.microservice.server.storage.entity;

import com.vinesmario.microservice.server.common.entity.BaseEntity;
import lombok.Data;

/**
 * @author
 * @date
 */

@Data
public class StorageFile extends BaseEntity<Long> {

    /**
     * 
     */
    private Long tenantId;
    /**
     * UUID
     */
    private String uuid;
    /**
     * 文件扩展名
     */
    private String fileExtension;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件存储绝对路径
     */
    private String fileAbsolutePath;
    /**
     * 文件访问绝对url
     */
    private String fileAbsoluteUrl;
    /**
     * 文件访问相对url
     */
    private String fileRelativeUrl;
    /**
     * 文件大小，单位B
     */
    private Long fileSize;


}
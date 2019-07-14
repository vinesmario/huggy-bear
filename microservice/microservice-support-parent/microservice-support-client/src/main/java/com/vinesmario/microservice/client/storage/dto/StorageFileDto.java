package com.vinesmario.microservice.client.storage.dto;

import com.vinesmario.common.constant.DictConstant;
import com.vinesmario.microservice.client.common.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author
 * @date
 */
@ApiModel(value = "StorageFile", description = "StorageFile")
@Data
public class StorageFileDto extends BaseDto<Long> {

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

    /**
     * alert param
     */
    public String getAlertParam() {
        return fileName;
    }
}
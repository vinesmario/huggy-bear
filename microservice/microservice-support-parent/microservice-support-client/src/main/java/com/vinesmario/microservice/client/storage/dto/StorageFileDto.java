package com.vinesmario.microservice.client.storage.dto;

import com.vinesmario.microservice.client.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author
 * @date
 */
@ApiModel(value = "StorageFileDto", description = "StorageFile数据传输对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StorageFileDto extends BaseDto<Long> {

    /**
     * 
     */
    @ApiModelProperty(value = "")
    private Long tenantId;
    /**
     * UUID
     */
    @ApiModelProperty(value = "UUID")
    private String uuid;
    /**
     * 文件扩展名
     */
    @ApiModelProperty(value = "文件扩展名")
    private String fileExtension;
    /**
     * 文件名称
     */
    @ApiModelProperty(value = "文件名称")
    private String fileName;
    /**
     * 文件存储绝对路径
     */
    @ApiModelProperty(value = "文件存储绝对路径")
    private String fileAbsolutePath;
    /**
     * 文件访问绝对url
     */
    @ApiModelProperty(value = "文件访问绝对url")
    private String fileAbsoluteUrl;
    /**
     * 文件访问相对url
     */
    @ApiModelProperty(value = "文件访问相对url")
    private String fileRelativeUrl;
    /**
     * 文件大小，单位B
     */
    @ApiModelProperty(value = "文件大小，单位B")
    private Long fileSize;
    /**
     * 文件MD5算法哈希值
     */
    @ApiModelProperty(value = "文件MD5算法哈希值")
    private String fileMd5Hex;
    /**
     * 文件SHA1算法哈希值
     */
    @ApiModelProperty(value = "文件SHA1算法哈希值")
    private String fileSha1Hex;

    /**
     * alert param
     */
    public String getAlertParam() {
        return fileName;
    }
}
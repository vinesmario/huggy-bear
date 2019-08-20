package com.vinesmario.microservice.client.storage.dto;

import com.vinesmario.microservice.client.common.dto.BaseDTO;
import com.vinesmario.microservice.client.document.excel.annotation.Excel;
import com.vinesmario.microservice.client.document.excel.annotation.ExcelColumn;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author
 * @date
 */
@Excel
@ApiModel(value = "StorageImageDTO", description = "StorageImage数据传输对象")
@Data
public class StorageImageDTO extends BaseDTO<Long> implements IStorageDTO {

    /**
     *
     */
    @ExcelColumn
    @ApiModelProperty(value = "租户ID")
    @NotBlank
    private Long tenantId;
    /**
     * UUID
     */
    @ExcelColumn
    @ApiModelProperty(value = "UUID")
    private String uuid;
    /**
     * 文件扩展名
     */
    @ExcelColumn
    @ApiModelProperty(value = "文件扩展名")
    private String fileExtension;
    /**
     * 文件名称
     */
    @ExcelColumn
    @ApiModelProperty(value = "文件名称")
    private String fileName;
    /**
     * 文件存储绝对路径
     */
    @ExcelColumn
    @ApiModelProperty(value = "文件存储绝对路径")
    private String fileAbsolutePath;
    /**
     * 文件访问绝对url
     */
    @ExcelColumn
    @ApiModelProperty(value = "文件访问绝对url")
    private String fileAbsoluteUrl;
    /**
     * 文件访问相对url
     */
    @ExcelColumn
    @ApiModelProperty(value = "文件访问相对url")
    private String fileRelativeUrl;
    /**
     * 文件大小，单位byte(B)
     */
    @ExcelColumn
    @ApiModelProperty(value = "文件大小，单位byte(B)")
    private Long fileSize;
    /**
     * 文件MD5算法哈希值
     */
    @ExcelColumn
    @ApiModelProperty(value = "文件MD5算法哈希值")
    private String fileMd5Hex;
    /**
     * 文件SHA1算法哈希值
     */
    @ExcelColumn
    @ApiModelProperty(value = "文件SHA1算法哈希值")
    private String fileSha1Hex;
    /**
     * 图片高度
     */
    @ExcelColumn
    @ApiModelProperty(value = "图片高度")
    private Integer imageHeight;
    /**
     * 图片宽度
     */
    @ExcelColumn
    @ApiModelProperty(value = "图片宽")
    private Integer imageWidth;

    /**
     * alert param
     */
    public String getAlertParam() {
        return fileName;
    }
}
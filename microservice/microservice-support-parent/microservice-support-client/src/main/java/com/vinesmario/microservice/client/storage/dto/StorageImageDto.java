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
@ApiModel(value = "StorageImageDto", description = "StorageImage数据传输对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StorageImageDto extends BaseDto<Long> {

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
     * 图片名称
     */
    @ApiModelProperty(value = "图片名称")
    private String imageName;
    /**
     * 图片存储绝对路径
     */
    @ApiModelProperty(value = "图片存储绝对路径")
    private String imageAbsolutePath;
    /**
     * 图片访问绝对url
     */
    @ApiModelProperty(value = "图片访问绝对url")
    private String imageAbsoluteUrl;
    /**
     * 图片访问相对url
     */
    @ApiModelProperty(value = "图片访问相对url")
    private String imageRelativeUrl;
    /**
     * 图片高度
     */
    @ApiModelProperty(value = "图片高度")
    private Integer imageHeight;
    /**
     * 图片宽
     */
    @ApiModelProperty(value = "图片宽")
    private Integer imageWidth;
    /**
     * 图片大小，单位B
     */
    @ApiModelProperty(value = "图片大小，单位B")
    private Long imageSize;
    /**
     * 图片MD5算法哈希值
     */
    @ApiModelProperty(value = "图片MD5算法哈希值")
    private String imageMd5Hex;
    /**
     * 图片SHA1算法哈希值
     */
    @ApiModelProperty(value = "图片SHA1算法哈希值")
    private String imageSha1Hex;
    /**
     * 缩略图名称
     */
    @ApiModelProperty(value = "缩略图名称")
    private String thumbnailName;
    /**
     * 缩略图存储绝对路径
     */
    @ApiModelProperty(value = "缩略图存储绝对路径")
    private String thumbnailAbsolutePath;
    /**
     * 缩略图访问绝对url
     */
    @ApiModelProperty(value = "缩略图访问绝对url")
    private String thumbnailAbsoluteUrl;
    /**
     * 缩略图访问相对url
     */
    @ApiModelProperty(value = "缩略图访问相对url")
    private String thumbnailRelativeUrl;
    /**
     * 缩略图高度
     */
    @ApiModelProperty(value = "缩略图高度")
    private Integer thumbnailHeight;
    /**
     * 缩略图宽度
     */
    @ApiModelProperty(value = "缩略图宽度")
    private Integer thumbnailWidth;
    /**
     * 缩略图大小，单位B
     */
    @ApiModelProperty(value = "缩略图大小，单位B")
    private Integer thumbnailSize;
    /**
     * 缩略图MD5算法哈希值
     */
    @ApiModelProperty(value = "缩略图MD5算法哈希值")
    private String thumbnailMd5Hex;
    /**
     * 缩略图SHA1算法哈希值
     */
    @ApiModelProperty(value = "缩略图SHA1算法哈希值")
    private String thumbnailSha1Hex;

    /**
     * alert param
     */
    public String getAlertParam() {
        return imageName;
    }
}
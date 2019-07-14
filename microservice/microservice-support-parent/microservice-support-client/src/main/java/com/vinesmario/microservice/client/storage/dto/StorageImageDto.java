package com.vinesmario.microservice.client.storage.dto;

import com.vinesmario.microservice.client.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author
 * @date
 */
@ApiModel(value = "StorageImage", description = "StorageImage")
@Data
public class StorageImageDto extends BaseDto<Long> {

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
     * 图片名称
     */
    private String imageName;
    /**
     * 图片存储绝对路径
     */
    private String imageAbsolutePath;
    /**
     * 图片访问绝对url
     */
    private String imageAbsoluteUrl;
    /**
     * 图片访问相对url
     */
    private String imageRelativeUrl;
    /**
     * 图片高度
     */
    private Integer imageHeight;
    /**
     * 图片宽
     */
    private Integer imageWidth;
    /**
     * 图片大小，单位B
     */
    private Long imageSize;
    /**
     * 缩略图名称
     */
    private String thumbnailName;
    /**
     * 缩略图存储绝对路径
     */
    private String thumbnailAbsolutePath;
    /**
     * 缩略图访问绝对url
     */
    private String thumbnailAbsoluteUrl;
    /**
     * 缩略图访问相对url
     */
    private String thumbnailRelativeUrl;
    /**
     * 缩略图高度
     */
    private Integer thumbnailHeight;
    /**
     * 缩略图宽度
     */
    private Integer thumbnailWidth;
    /**
     * 缩略图大小，单位B
     */
    private Integer thumbnailSize;

    /**
     * alert param
     */
    public String getAlertParam() {
        return imageName;
    }
}
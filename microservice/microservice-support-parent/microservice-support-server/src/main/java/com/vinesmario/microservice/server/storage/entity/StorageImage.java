package com.vinesmario.microservice.server.storage.entity;

import com.vinesmario.microservice.server.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author
 * @date
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "storage_image")
public class StorageImage extends BaseEntity<Long> {

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
     * 图片名称
     */
    @Column(name = "IMAGE_NAME")
    private String imageName;
    /**
     * 图片存储绝对路径
     */
    @Column(name = "IMAGE_ABSOLUTE_PATH")
    private String imageAbsolutePath;
    /**
     * 图片访问绝对url
     */
    @Column(name = "IMAGE_ABSOLUTE_URL")
    private String imageAbsoluteUrl;
    /**
     * 图片访问相对url
     */
    @Column(name = "IMAGE_RELATIVE_URL")
    private String imageRelativeUrl;
    /**
     * 图片高度
     */
    @Column(name = "IMAGE_HEIGHT")
    private Integer imageHeight;
    /**
     * 图片宽
     */
    @Column(name = "IMAGE_WIDTH")
    private Integer imageWidth;
    /**
     * 图片大小，单位B
     */
    @Column(name = "IMAGE_SIZE")
    private Long imageSize;
    /**
     * 图片MD5算法哈希值
     */
    @Column(name = "IMAGE_MD5_HEX")
    private String imageMd5Hex;
    /**
     * 图片SHA1算法哈希值
     */
    @Column(name = "IMAGE_SHA1_HEX")
    private String imageSha1Hex;
    /**
     * 缩略图名称
     */
    @Column(name = "THUMBNAIL_NAME")
    private String thumbnailName;
    /**
     * 缩略图存储绝对路径
     */
    @Column(name = "THUMBNAIL_ABSOLUTE_PATH")
    private String thumbnailAbsolutePath;
    /**
     * 缩略图访问绝对url
     */
    @Column(name = "THUMBNAIL_ABSOLUTE_URL")
    private String thumbnailAbsoluteUrl;
    /**
     * 缩略图访问相对url
     */
    @Column(name = "THUMBNAIL_RELATIVE_URL")
    private String thumbnailRelativeUrl;
    /**
     * 缩略图高度
     */
    @Column(name = "THUMBNAIL_HEIGHT")
    private Integer thumbnailHeight;
    /**
     * 缩略图宽度
     */
    @Column(name = "THUMBNAIL_WIDTH")
    private Integer thumbnailWidth;
    /**
     * 缩略图大小，单位B
     */
    @Column(name = "THUMBNAIL_SIZE")
    private Integer thumbnailSize;
    /**
     * 缩略图MD5算法哈希值
     */
    @Column(name = "THUMBNAIL_MD5_HEX")
    private String thumbnailMd5Hex;
    /**
     * 缩略图SHA1算法哈希值
     */
    @Column(name = "THUMBNAIL_SHA1_HEX")
    private String thumbnailSha1Hex;


}
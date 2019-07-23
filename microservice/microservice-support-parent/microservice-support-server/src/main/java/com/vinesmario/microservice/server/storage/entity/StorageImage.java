package com.vinesmario.microservice.server.storage.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author
 * @date
 */

@Data
@Table(name = "storage_image")
public class StorageImage extends StorageFile {

    /**
     * 图片高度
     */
    @Column(name = "IMAGE_HEIGHT")
    private Integer imageHeight;
    /**
     * 图片宽度
     */
    @Column(name = "IMAGE_WIDTH")
    private Integer imageWidth;

}
package com.vinesmario.microservice.server.storage.entity;

import com.vinesmario.microservice.server.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
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
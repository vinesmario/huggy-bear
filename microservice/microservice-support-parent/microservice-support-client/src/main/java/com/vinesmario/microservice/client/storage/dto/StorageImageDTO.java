package com.vinesmario.microservice.client.storage.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author
 * @date
 */
@ApiModel(value = "StorageImageDTO", description = "StorageImage数据传输对象")
@Data
public class StorageImageDTO extends StorageFileDTO {

    /**
     * 图片高度
     */
    @ApiModelProperty(value = "图片高度")
    private Integer imageHeight;
    /**
     * 图片宽度
     */
    @ApiModelProperty(value = "图片宽")
    private Integer imageWidth;

}
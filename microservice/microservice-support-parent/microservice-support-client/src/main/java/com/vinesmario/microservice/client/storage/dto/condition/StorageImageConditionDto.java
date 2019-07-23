package com.vinesmario.microservice.client.storage.dto.condition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author
 * @date
 */
@ApiModel(value = "StorageImageConditionDto", description = "StorageImage筛选条件数据传输对象")
@Data
public class StorageImageConditionDto extends StorageFileConditionDto {

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
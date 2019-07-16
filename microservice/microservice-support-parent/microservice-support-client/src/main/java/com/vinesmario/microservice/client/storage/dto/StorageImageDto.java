package com.vinesmario.microservice.client.storage.dto;

import com.vinesmario.microservice.client.common.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class StorageImageDto extends StorageFileDto {

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

    /**
     * alert param
     */
    public String getAlertParam() {
        return getFileName();
    }

}
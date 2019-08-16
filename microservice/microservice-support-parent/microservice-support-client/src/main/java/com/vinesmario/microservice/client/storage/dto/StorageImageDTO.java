package com.vinesmario.microservice.client.storage.dto;

import com.vinesmario.microservice.client.document.excel.annotation.Excel;
import com.vinesmario.microservice.client.document.excel.annotation.ExcelColumn;
import com.vinesmario.microservice.client.storage.web.feign.StorageImageClient;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author
 * @date
 */
@Excel(feignClient = StorageImageClient.class)
@ApiModel(value = "StorageImageDTO", description = "StorageImage数据传输对象")
@Data
public class StorageImageDTO extends StorageFileDTO {

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

}
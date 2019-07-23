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
@ApiModel(value = "StoragePdfSplitedDto", description = "StoragePdfSplited数据传输对象")
@Data
public class StoragePdfSplitedDto extends StorageFileDto {

    /**
     * 来源ID
     */
    @ApiModelProperty(value = "来源ID")
    private Long originId;
    /**
     * 来源UUID
     */
    @ApiModelProperty(value = "来源UUID")
    private String originUuid;
    /**
     * 页码，从第1页开始
     */
    @ApiModelProperty(value = "页码，从第1页开始")
    private Integer pagination;

}
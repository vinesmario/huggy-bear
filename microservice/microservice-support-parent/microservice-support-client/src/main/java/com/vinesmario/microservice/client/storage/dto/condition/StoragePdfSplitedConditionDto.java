package com.vinesmario.microservice.client.storage.dto.condition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author
 * @date
 */
@ApiModel(value = "StoragePdfSplitedConditionDTO", description = "StoragePdfSplited筛选条件数据传输对象")
@Data
public class StoragePdfSplitedConditionDTO extends StorageFileConditionDTO {

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
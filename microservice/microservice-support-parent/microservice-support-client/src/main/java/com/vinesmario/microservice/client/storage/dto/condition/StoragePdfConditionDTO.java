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
@ApiModel(value = "StoragePdfConditionDTO", description = "StoragePdf筛选条件数据传输对象")
@Data
public class StoragePdfConditionDTO extends StorageFileConditionDTO {

    /**
     * 页数
     */
    @ApiModelProperty(value = "页数")
    private Integer pdfPageCount;
    /**
     * 已拆分,0-否;1-是
     */
    @ApiModelProperty(value = "已拆分,0-否;1-是")
    private Byte splited;

}
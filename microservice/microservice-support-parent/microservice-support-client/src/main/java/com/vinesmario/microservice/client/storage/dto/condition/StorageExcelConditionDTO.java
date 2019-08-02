package com.vinesmario.microservice.client.storage.dto.condition;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author
 * @date
 */
@ApiModel(value = "StorageExcelConditionDTO", description = "StorageExcel筛选条件数据传输对象")
@Data
public class StorageExcelConditionDTO extends StorageFileConditionDTO {

}
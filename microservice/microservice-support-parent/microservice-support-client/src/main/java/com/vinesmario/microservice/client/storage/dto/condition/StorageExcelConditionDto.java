package com.vinesmario.microservice.client.storage.dto.condition;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author
 * @date
 */
@ApiModel(value = "StorageExcelConditionDto", description = "StorageExcel筛选条件数据传输对象")
@Data
public class StorageExcelConditionDto extends StorageFileConditionDto {

}
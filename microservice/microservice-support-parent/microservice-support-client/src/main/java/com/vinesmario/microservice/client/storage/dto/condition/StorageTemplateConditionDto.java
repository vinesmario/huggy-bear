package com.vinesmario.microservice.client.storage.dto.condition;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author
 * @date
 */
@ApiModel(value = "StorageTemplateConditionDto", description = "StorageTemplate筛选条件数据传输对象")
@Data
public class StorageTemplateConditionDto extends StorageFileConditionDto {

}
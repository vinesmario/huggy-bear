package com.vinesmario.microservice.client.storage.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author
 * @date
 */
@ApiModel(value = "StorageTemplateDto", description = "StorageTemplate数据传输对象")
@Data
public class StorageTemplateDto extends StorageFileDto {

}
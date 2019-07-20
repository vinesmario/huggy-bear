<#include "/java_copyright.include">
package com.vinesmario.microservice.client.demo;

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
@ApiModel(value = "${basepackageCap}DemoDto", description = "${basepackageCap}Demo数据传输对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ${basepackageCap}DemoDto extends BaseDto<Long> {

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * alert param
     */
    public String getAlertParam() {
        return name;
    }

}
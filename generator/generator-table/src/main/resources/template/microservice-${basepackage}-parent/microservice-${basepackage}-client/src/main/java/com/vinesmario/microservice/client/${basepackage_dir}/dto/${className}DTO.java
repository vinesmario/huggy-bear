<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package com.vinesmario.microservice.client.${basepackage}.dto;

import com.vinesmario.microservice.client.common.dto.BaseDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author
 * @date
 */
@ApiModel(value = "${className}DTO", description = "${className}数据传输对象")
@Data
public class ${className}DTO extends BaseDTO<Long> {

    <#list table.columns as column>
        <#if column.columnNameLower != 'id'
                && column.columnNameLower != 'createdBy'
                && column.columnNameLower != 'createdDate'
                && column.columnNameLower != 'lastModifiedBy'
                && column.columnNameLower != 'lastModifiedDate'
                && column.columnNameLower != 'memo'
                && column.columnNameLower != 'deleted'>
    /**
     * ${column.remarks}
     */
            <#if column.simpleJavaType == 'LocalDateTime'>
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = DictConstant.DEFAULT_TIMEZONE)
            <#elseif column.simpleJavaType == 'LocalDate'>
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = DictConstant.DEFAULT_TIMEZONE)
            <#elseif column.simpleJavaType == 'LocalTime'>
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = DictConstant.DEFAULT_TIMEZONE)
            <#else>
            </#if>
    @ApiModelProperty(value = "${column.remarks}")
    private ${column.simpleJavaType} ${column.columnNameLower};
        </#if>
    </#list>

    /**
     * alert param
     */
    public String getAlertParam() {
        return name;
    }
}
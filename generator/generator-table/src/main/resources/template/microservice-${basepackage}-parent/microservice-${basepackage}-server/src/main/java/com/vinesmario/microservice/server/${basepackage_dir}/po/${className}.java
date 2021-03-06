<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package com.vinesmario.microservice.server.${basepackage}.po;

import com.vinesmario.microservice.server.common.po.BasePO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author
 * @date
 */
@Data
@Table(name = "${table.sqlName}")
public class ${className} extends BasePO<Long> {

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
    @Column(name = "${column.constantName}")
    private ${column.simpleJavaType} ${column.columnNameLower};
        </#if>
    </#list>


}
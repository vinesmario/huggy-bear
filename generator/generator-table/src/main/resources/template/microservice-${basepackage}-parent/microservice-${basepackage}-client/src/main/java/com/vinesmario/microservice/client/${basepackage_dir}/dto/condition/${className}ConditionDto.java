<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package com.vinesmario.microservice.client.${basepackage}.dto.condition;

import com.vinesmario.microservice.client.common.dto.condition.ConditionDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author
 * @date
 */
@ApiModel(value = "${className}ConditionDto", description = "${className}筛选条件数据传输对象")
@Data
public class ${className}ConditionDto implements ConditionDto {

    /**
     * ID
     */
	@ApiModelProperty(value = "ID")
    private Long id;
    /**
     * ID为空，true表示查询记录为空
     */
	@ApiModelProperty(value = "空ID")
    private boolean idIsNull = false;
    /**
     * ID列表
     */
	@ApiModelProperty(value = "ID列表")
    private List<Long> ids;
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
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = DEFAULT_TIMEZONE)
	private String ${column.columnNameLower};
			<#elseif column.simpleJavaType == 'LocalDate'>
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = DEFAULT_TIMEZONE)
	private String ${column.columnNameLower};
			<#elseif column.simpleJavaType == 'LocalTime'>
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = DEFAULT_TIMEZONE)
	private String ${column.columnNameLower};
			<#else>
	@ApiModelProperty(value = "${column.remarks}")
	private ${column.simpleJavaType} ${column.columnNameLower};
			</#if>
        </#if>
    </#list>

	// 分页参数
	/**
	 * 页码
	 */
	@ApiModelProperty(value = "页码")
	private Integer pageNumber;
	/**
	 * 每页页记录数
	 */
	@ApiModelProperty(value = "每页页记录数")
	private Integer pageSize;
	/**
	 * 排序
	 */
	@ApiModelProperty(value = "排序")
	private String[] sort;
}
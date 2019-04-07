<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.dto.condition;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author
 * @date
 */
@ApiModel(value = "${className}", description = "${className}")
@Data
public class ${className}ConditionDto extends ConditionDto {

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    /**
     * ID
     */
    private Long id;
    /**
     * ID为空，true表示查询记录为空
     */
    private boolean idIsNull=false;
    /**
     * ID列表
     */
    private List<Integer> ids;
    <#list table.columns as column>
        <#if column.columnNameLower != 'id'
                && column.columnNameLower != 'createdBy'
                && column.columnNameLower != 'createdDate'
                && column.columnNameLower != 'lastModifiedBy'
                && column.columnNameLower != 'lastModifiedDate'
                && column.columnNameLower != 'memo'
                && column.columnNameLower != 'delFlag'>
    /**
     * ${column.remarks}
     */
			<#if column.simpleJavaType == 'LocalDateTime'>
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private String ${column.columnNameLower};
			<#elseif column.simpleJavaType == 'LocalDate'>
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private String ${column.columnNameLower};
			<#elseif column.simpleJavaType == 'LocalTime'>
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "GMT+8")
	private String ${column.columnNameLower};
			<#else>
	private ${column.simpleJavaType} ${column.columnNameLower};
			</#if>
        </#if>
    </#list>


}
<#include "/java_copyright.include">
package com.vinesmario.microservice.client.demo;

import com.vinesmario.microservice.client.common.dto.condition.ConditionDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author
 * @date
 */
@ApiModel(value = "${basepackageCap}DemoConditionDTO", description = "${basepackageCap}Demo筛选条件数据传输对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ${basepackageCap}DemoConditionDTO implements ConditionDTO {

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
    /**
     * 名称
     */
	@ApiModelProperty(value = "名称")
    private String name;

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
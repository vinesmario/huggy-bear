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
@ApiModel(value = "ScheduleDemoConditionDTO", description = "ScheduleDemo筛选条件数据传输对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDemoConditionDTO implements ConditionDTO {

    /**
     * ID
     */
    private Long id;
    /**
     * ID为空，true表示查询记录为空
     */
    private boolean idIsNull = false;
    /**
     * ID列表
     */
    private List<Long> ids;
    /**
     * 名称
     */
    private String name;

	/**
	 * 分页参数
	 */
	private Integer pageNumber;
	private Integer pageSize;
	private String[] sort;
}
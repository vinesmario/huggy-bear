package com.vinesmario.microservice.client.demo;

import com.vinesmario.microservice.client.common.dto.condition.ConditionDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author
 * @date
 */
@ApiModel(value = "UaaDemoConditionDTO", description = "UaaDemoConditionDTO")
@Data
public class UaaDemoConditionDTO implements ConditionDTO {

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
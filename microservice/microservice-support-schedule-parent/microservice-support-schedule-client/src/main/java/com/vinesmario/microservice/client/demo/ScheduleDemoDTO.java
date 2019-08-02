package com.vinesmario.microservice.client.demo;

import com.vinesmario.microservice.client.common.dto.BaseDTO;
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
@ApiModel(value = "ScheduleDemoDTO", description = "ScheduleDemo数据传输对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDemoDTO extends BaseDTO<Long> {

    /**
     * 名称
     */
    private String name;

    /**
     * alert param
     */
    public String getAlertParam() {
        return name;
    }
}
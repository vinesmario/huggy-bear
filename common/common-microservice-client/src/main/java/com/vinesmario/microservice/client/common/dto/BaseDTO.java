package com.vinesmario.microservice.client.common.dto;

import com.vinesmario.common.constant.DictConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public abstract class BaseDTO<PK extends Serializable> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID，主键
     */
    private PK id;
    /**
     * 备注
     */
    private String memo;
    /**
     * 是否删除 0-否；1-是
     */
    private Byte deleted;
    /**
     * 创建者
     */
    private Long createdBy;
    /**
     * 创建时间
     * 服务器所在时区
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = DictConstant.DEFAULT_TIMEZONE)
    private LocalDateTime createdDate;
    /**
     * 最后修改者
     */
    private Long lastModifiedBy;
    /**
     * 最后修改时间
     * 服务器所在时区
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = DictConstant.DEFAULT_TIMEZONE)
    private LocalDateTime lastModifiedDate;

    public abstract String getAlertParam();

}

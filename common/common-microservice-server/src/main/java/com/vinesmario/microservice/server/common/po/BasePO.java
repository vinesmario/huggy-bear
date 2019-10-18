package com.vinesmario.microservice.server.common.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vinesmario.common.constant.DictConstant;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Base abstract class for entities which will
 * Create new records
 * Read existing records
 * Update existing records
 * Delete existing records.
 */
@Data
@MappedSuperclass
public class BasePO<PK extends Serializable> {

    /**
     * ID，主键
     */
    @Column(name = "ID")
    private PK id;
    /**
     * 创建者
     */
    @Column(name = "CREATED_BY")
    private Long createdBy;
    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = DictConstant.DEFAULT_TIMEZONE)
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;
    /**
     * 最后修改者
     */
    @Column(name = "LAST_MODIFIED_BY")
    private Long lastModifiedBy;
    /**
     * 最后修改时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = DictConstant.DEFAULT_TIMEZONE)
    @Column(name = "LAST_MODIFIED_DATE")
    private LocalDateTime lastModifiedDate;
    /**
     * 备注
     */
    @Column(name = "MEMO")
    private String memo;
    /**
     * 删除标识 0-否；1-是
     */
    @Column(name = "DELETED")
    private Byte deleted;

}

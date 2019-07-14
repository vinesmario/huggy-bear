package com.vinesmario.microservice.client.storage.dto.condition;

import com.vinesmario.microservice.client.common.dto.condition.ConditionDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author
 * @date
 */
@ApiModel(value = "StorageFile", description = "StorageFile")
@Data
public class StorageFileConditionDto implements ConditionDto {

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
     * 
     */
	private Long tenantId;
    /**
     * UUID
     */
	private String uuid;
    /**
     * 文件扩展名
     */
	private String fileExtension;
    /**
     * 文件名称
     */
	private String fileName;
    /**
     * 文件存储绝对路径
     */
	private String fileAbsolutePath;
    /**
     * 文件访问绝对url
     */
	private String fileAbsoluteUrl;
    /**
     * 文件访问相对url
     */
	private String fileRelativeUrl;
    /**
     * 文件大小，单位B
     */
	private Long fileSize;

	/**
	 * 分页参数
	 */
	private Integer pageNumber;
	private Integer pageSize;
	private String[] sort;
}
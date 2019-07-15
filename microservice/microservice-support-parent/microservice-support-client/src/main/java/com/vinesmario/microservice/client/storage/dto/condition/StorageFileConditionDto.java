package com.vinesmario.microservice.client.storage.dto.condition;

import com.vinesmario.microservice.client.common.dto.condition.ConditionDto;
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
@ApiModel(value = "StorageFileConditionDto", description = "StorageFile筛选条件数据传输对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
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
	@ApiModelProperty(value = "")
	private Long tenantId;
    /**
     * UUID
     */
	@ApiModelProperty(value = "UUID")
	private String uuid;
    /**
     * 文件扩展名
     */
	@ApiModelProperty(value = "文件扩展名")
	private String fileExtension;
    /**
     * 文件名称
     */
	@ApiModelProperty(value = "文件名称")
	private String fileName;
    /**
     * 文件存储绝对路径
     */
	@ApiModelProperty(value = "文件存储绝对路径")
	private String fileAbsolutePath;
    /**
     * 文件访问绝对url
     */
	@ApiModelProperty(value = "文件访问绝对url")
	private String fileAbsoluteUrl;
    /**
     * 文件访问相对url
     */
	@ApiModelProperty(value = "文件访问相对url")
	private String fileRelativeUrl;
    /**
     * 文件大小，单位B
     */
	@ApiModelProperty(value = "文件大小，单位B")
	private Long fileSize;
    /**
     * 文件MD5算法哈希值
     */
	@ApiModelProperty(value = "文件MD5算法哈希值")
	private String fileMd5Hex;
    /**
     * 文件SHA1算法哈希值
     */
	@ApiModelProperty(value = "文件SHA1算法哈希值")
	private String fileSha1Hex;

	/**
	 * 分页参数
	 */
	private Integer pageNumber;
	private Integer pageSize;
	private String[] sort;
}
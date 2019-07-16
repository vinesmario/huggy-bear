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
@ApiModel(value = "StorageImageConditionDto", description = "StorageImage筛选条件数据传输对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StorageImageConditionDto implements ConditionDto {

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
     * 图片名称
     */
	@ApiModelProperty(value = "图片名称")
	private String imageName;
    /**
     * 图片存储绝对路径
     */
	@ApiModelProperty(value = "图片存储绝对路径")
	private String imageAbsolutePath;
    /**
     * 图片访问绝对url
     */
	@ApiModelProperty(value = "图片访问绝对url")
	private String imageAbsoluteUrl;
    /**
     * 图片访问相对url
     */
	@ApiModelProperty(value = "图片访问相对url")
	private String imageRelativeUrl;
    /**
     * 图片高度
     */
	@ApiModelProperty(value = "图片高度")
	private Integer imageHeight;
    /**
     * 图片宽
     */
	@ApiModelProperty(value = "图片宽")
	private Integer imageWidth;
    /**
     * 图片大小，单位B
     */
	@ApiModelProperty(value = "图片大小，单位B")
	private Long imageSize;
    /**
     * 图片MD5算法哈希值
     */
	@ApiModelProperty(value = "图片MD5算法哈希值")
	private String imageMd5Hex;
    /**
     * 图片SHA1算法哈希值
     */
	@ApiModelProperty(value = "图片SHA1算法哈希值")
	private String imageSha1Hex;

	/**
	 * 分页参数
	 */
	private Integer pageNumber;
	private Integer pageSize;
	private String[] sort;
}
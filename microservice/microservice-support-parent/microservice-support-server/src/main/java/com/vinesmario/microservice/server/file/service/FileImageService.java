package com.vinesmario.microservice.server.file.service;

import com.vinesmario.common.constant.DictConstant;
import com.vinesmario.microservice.client.common.dto.condition.ConditionDto;
import com.vinesmario.microservice.client.file.dto.FileImageDto;
import com.vinesmario.microservice.client.file.dto.condition.FileImageConditionDto;
import com.vinesmario.microservice.server.common.persistence.mybatis.BaseExample;
import com.vinesmario.microservice.server.common.service.mybatis.impl.BaseService;
import com.vinesmario.microservice.server.file.entity.FileImage;
import com.vinesmario.microservice.server.file.mapper.FileImageMapper;
import com.vinesmario.microservice.server.file.mapstruct.FileImageMapStruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

/**
 * @author maodipu
 * @date 2018-01-18
 */
@Slf4j
@Service
public class FileImageService extends BaseService<FileImageDto, FileImage, Long>{

	private final FileImageMapper mapper;
	private final FileImageMapStruct mapStruct;

	public FileImageService(FileImageMapper mapper,
								@Qualifier("fileImageMapStructImpl") FileImageMapStruct mapStruct) {
		super(mapper, mapStruct);
		this.mapper = mapper;
		this.mapStruct = mapStruct;
	}

	public BaseExample fromConditionDto2Example(ConditionDto conditionDto) {
		BaseExample example = new BaseExample();
		BaseExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(DictConstant.BYTE_YES_NO_N);

		if (!ObjectUtils.isEmpty(conditionDto)) {
			FileImageConditionDto fileImageConditionDto = (FileImageConditionDto) conditionDto;
			if (!ObjectUtils.isEmpty(fileImageConditionDto.getId())) {
				criteria.andIdEqualTo(fileImageConditionDto.getId());
			}
			if (!CollectionUtils.isEmpty(fileImageConditionDto.getIds())) {
                criteria.andIdIn(fileImageConditionDto.getIds());
            }
		}
		return example;
	}


}

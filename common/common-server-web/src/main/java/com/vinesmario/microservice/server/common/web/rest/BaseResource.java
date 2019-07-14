package com.vinesmario.microservice.server.common.web.rest;

import com.vinesmario.common.constant.DictConstant;
import com.vinesmario.microservice.client.common.dto.BaseDto;
import com.vinesmario.microservice.client.common.dto.condition.ConditionDto;
import com.vinesmario.microservice.client.common.web.feign.CrudClient;
import com.vinesmario.microservice.server.common.service.mybatis.CrudService;
import com.vinesmario.microservice.server.common.web.rest.errors.BadRequestAlertException;
import com.vinesmario.microservice.server.common.web.rest.util.HeaderUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class BaseResource<DTO extends BaseDto, CONDITION extends ConditionDto, PK extends Serializable>
        extends SimpleResource<DTO, CONDITION, PK>
        implements CrudClient<DTO, CONDITION, PK> {

    protected String entityName;
    private final CrudService<DTO, PK> service;

    public BaseResource(CrudService<DTO, PK> service) {
        super(service);
        this.service = service;
    }

    @ApiOperation(value = "添加", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "添加成功", response = String.class)
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<DTO> create(@RequestBody DTO dto) {
        if (!ObjectUtils.isEmpty(dto.getId())) {
            throw new BadRequestAlertException("A new " + entityName + " cannot already have an ID",
                    null, "id.exists", entityName);
        }
        service.create(dto);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityCreationAlert(entityName, dto.getAlertParam()))
                .body(dto);
    }

    @ApiOperation(value = "更新", httpMethod = "PUT", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "更新成功", response = String.class)
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<DTO> modify(@PathVariable("id") PK id,
                                      @RequestBody DTO dto) {
        dto.setId(id);
        dto.setDeleted(DictConstant.BYTE_YES_NO_N);
        service.modify(dto);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(entityName, dto.getAlertParam()))
                .body(dto);
    }

    @ApiOperation(value = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "删除成功", response = String.class)
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable("id") PK id) {
        Optional<DTO> optional = service.get(id);
        String alertParam = "";
        if (optional.isPresent()) {
            service.delete(optional.get());
            alertParam = optional.get().getAlertParam();
        }
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityDeletionAlert(entityName, alertParam))
                .build();
    }

    @ApiOperation(value = "批量删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "批量删除成功", response = String.class)
    @DeleteMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<Void> deleteByExample(@RequestBody CONDITION conditionDto) {
        preConditionDto(conditionDto);
        List<DTO> list = service.list(conditionDto);
        String alertParam = "";
        if (!CollectionUtils.isEmpty(list)) {
            service.deleteIterable(list);
            alertParam = StringUtils.join(list.stream().map(dto -> dto.getAlertParam()).collect(Collectors.toList()), ",");
        }
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityDeletionCollectionAlert(entityName, alertParam))
                .build();
    }

}

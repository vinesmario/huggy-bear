package com.vinesmario.microservice.server.common.web.rest;

import com.vinesmario.microservice.client.common.dto.BaseDto;
import com.vinesmario.microservice.client.common.dto.condition.ConditionDto;
import com.vinesmario.microservice.client.common.web.feign.CrudClient;
import com.vinesmario.microservice.server.common.constant.DictConstant;
import com.vinesmario.microservice.server.common.service.CrudService;
import com.vinesmario.microservice.server.common.web.rest.errors.BadRequestAlertException;
import com.vinesmario.microservice.server.common.web.rest.util.HeaderUtil;
import com.vinesmario.microservice.server.common.web.rest.util.PaginationUtil;
import com.vinesmario.microservice.server.common.web.rest.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Data
public abstract class BaseResource<DTO extends BaseDto, CONDITION extends ConditionDto, PK extends Serializable>
        extends SimpleResource<DTO, CONDITION, PK>
        implements CrudClient<DTO, CONDITION, PK> {

    private String entityName;
    private CrudService<DTO, PK> service;

    @ApiOperation(value = "添加", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "添加成功", response = String.class)
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<DTO> create(@RequestBody DTO dto) {
        if (!ObjectUtils.isEmpty(dto.getId())) {
            throw new BadRequestAlertException("A new " + getEntityName() + " cannot already have an ID",
                    getEntityName(), "idexists");
        }
        getService().create(dto);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityCreationAlert(getEntityName(), dto.getId().toString()))
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
        getService().modify(dto);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(getEntityName(), dto.getId().toString()))
                .body(dto);
    }

    @ApiOperation(value = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "删除成功", response = String.class)
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable("id") PK id) {
        Optional<DTO> optional = getService().get(id);
        if (optional.isPresent()) {
            optional.get().setDeleted(DictConstant.BYTE_YES_NO_Y);
            getService().modify(optional.get());
        }
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityDeletionAlert(getEntityName(), id.toString()))
                .build();
    }

}

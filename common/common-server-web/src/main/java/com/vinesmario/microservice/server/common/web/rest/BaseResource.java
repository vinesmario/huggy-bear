package com.vinesmario.microservice.server.common.web.rest;

import com.vinesmario.microservice.client.common.dto.BaseDto;
import com.vinesmario.microservice.client.common.dto.condition.ConditionDto;
import com.vinesmario.microservice.client.common.web.feign.CrudClient;
import com.vinesmario.microservice.server.common.constant.DictConstant;
import com.vinesmario.microservice.server.common.service.CrudService;
import com.vinesmario.microservice.server.common.web.rest.errors.BadRequestAlertException;
import com.vinesmario.microservice.server.common.web.rest.util.HeaderUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Optional;

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
            throw new BadRequestAlertException("A new " + this.entityName + " cannot already have an ID",
                    this.entityName, "idexists");
        }
        this.service.create(dto);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityCreationAlert(this.entityName, dto.getId().toString()))
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
        this.service.modify(dto);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(this.entityName, dto.getId().toString()))
                .body(dto);
    }

    @ApiOperation(value = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "删除成功", response = String.class)
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable("id") PK id) {
        Optional<DTO> optional = this.service.get(id);
        if (optional.isPresent()) {
            optional.get().setDeleted(DictConstant.BYTE_YES_NO_Y);
            this.service.modify(optional.get());
        }
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityDeletionAlert(this.entityName, id.toString()))
                .build();
    }

}

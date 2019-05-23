package com.vinesmario.microservice.server.uaa.web.rest.v1;

import com.vinesmario.microservice.client.uaa.dto.UserAccountDto;
import com.vinesmario.microservice.client.uaa.dto.condition.UserAccountConditionDto;
import com.vinesmario.microservice.client.uaa.web.feign.UserAccountClient;
import com.vinesmario.microservice.server.common.constant.DictConstant;
import com.vinesmario.microservice.server.common.web.rest.BaseResource;
import com.vinesmario.microservice.server.common.web.rest.errors.BadRequestAlertException;
import com.vinesmario.microservice.server.common.web.rest.util.HeaderUtil;
import com.vinesmario.microservice.server.uaa.service.UserAccountService;
import com.vinesmario.microservice.server.uaa.web.rest.errors.EmailAlreadyUsedException;
import com.vinesmario.microservice.server.uaa.web.rest.errors.MobileAlreadyUsedException;
import com.vinesmario.microservice.server.uaa.web.rest.errors.UsernameAlreadyUsedException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author
 * @date
 */

@Api(description = "UserAccountCRUD", tags = "UserAccountController", basePath = "/user_account")
@RestController
@RequestMapping("/api/v1/user_account")
public class UserAccountResource extends BaseResource<UserAccountDto, UserAccountConditionDto, Long>
        implements UserAccountClient {

    private final UserAccountService service;

    public UserAccountResource(UserAccountService service) {
        super(service);
        this.service = service;
        this.entityName = "UserAccount";
    }

    @Override
    public void preConditionDto(UserAccountConditionDto queryDto) {

    }

    @ApiOperation(value = "添加", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "添加成功", response = String.class)
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @Override
    public ResponseEntity<UserAccountDto> create(@RequestBody UserAccountDto dto) {
        if (!ObjectUtils.isEmpty(dto.getId())) {
            throw new BadRequestAlertException("A new " + this.entityName + " cannot already have an ID",
                    null, "id.exists", this.entityName);
        } else if (this.service.getByUsername(dto.getUsername()).isPresent()) {
            throw new UsernameAlreadyUsedException(this.entityName);
        } else if (this.service.getByMobile(dto.getMobile()).isPresent()) {
            throw new MobileAlreadyUsedException(this.entityName);
        } else if (this.service.getByEmail(dto.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException(this.entityName);
        } else {
            this.service.create(dto);
            return ResponseEntity.ok()
                    .headers(HeaderUtil.createEntityCreationAlert(this.entityName, dto.getId().toString()))
                    .body(dto);
        }
    }

    @ApiOperation(value = "更新", httpMethod = "PUT", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "更新成功", response = String.class)
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<UserAccountDto> modify(@PathVariable("id") Long id,
                                                 @RequestBody UserAccountDto dto) {
        Optional<UserAccountDto> optional = this.service.getByUsername(dto.getUsername());
        if (optional.isPresent() && optional.get().getId().equals(id)) {
            throw new UsernameAlreadyUsedException(this.entityName);
        }
        optional = this.service.getByMobile(dto.getMobile());
        if (optional.isPresent() && optional.get().getId().equals(id)) {
            throw new MobileAlreadyUsedException(this.entityName);
        }
        optional = this.service.getByEmail(dto.getEmail());
        if (optional.isPresent() && optional.get().getId().equals(id)) {
            throw new EmailAlreadyUsedException(this.entityName);
        }
        dto.setId(id);
        dto.setDeleted(DictConstant.BYTE_YES_NO_N);
        this.service.modify(dto);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(this.entityName, dto.getId().toString()))
                .body(dto);
    }
}

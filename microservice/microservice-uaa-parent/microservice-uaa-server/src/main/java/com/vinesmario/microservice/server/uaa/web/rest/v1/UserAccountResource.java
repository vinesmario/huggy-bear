package com.vinesmario.microservice.server.uaa.web.rest.v1;

import com.vinesmario.common.constant.DictConstant;
import com.vinesmario.microservice.client.uaa.dto.UserAccountDTO;
import com.vinesmario.microservice.client.uaa.dto.condition.UserAccountConditionDTO;
import com.vinesmario.microservice.client.uaa.web.feign.UserAccountClient;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author
 * @date
 */

@Api(description = "UserAccountCRUD", tags = "UserAccountController", basePath = "/user_account")
@Slf4j
@RestController
@RequestMapping("/api/v1/user_account")
public class UserAccountResource extends BaseResource<UserAccountDTO, UserAccountConditionDTO, Long>
        implements UserAccountClient {

    private final UserAccountService service;

    public UserAccountResource(UserAccountService service) {
        super(service);
        this.service = service;
        this.entityName = "UserAccount";
    }

    @Override
    public void preConditionDTO(UserAccountConditionDTO queryDTO) {

    }

    @ApiOperation(value = "查询列表，有分页参数则分页", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "查询成功", response = String.class)
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<List<UserAccountDTO>> search(@ModelAttribute UserAccountConditionDTO conditionDTO) {
        preConditionDTO(conditionDTO);
        // 创建分页对象PageRequest
        String[] directionParameter = conditionDTO.getSort();
        Sort sort = null;
        if (!ObjectUtils.isEmpty(directionParameter)) {
            sort = parseParameterIntoSort(directionParameter, DEFAULT_PROPERTY_DELIMITER);
        }

        if (ObjectUtils.isEmpty(conditionDTO.getPageNumber())
                || ObjectUtils.isEmpty(conditionDTO.getPageSize())) {
            // 分页参数不全
            List<UserAccountDTO> list = service.list(conditionDTO, sort);
            return ResponseEntity.ok().body(list);
        } else {
            Pageable pageable = PageRequest.of(conditionDTO.getPageNumber(), conditionDTO.getPageSize(), sort);
            Page<UserAccountDTO> page = service.page(conditionDTO, pageable);
            return ResponseEntity.ok()
                    .headers(HeaderUtil.createPage(page))
                    .body(page.getContent());
        }
    }

    @ApiOperation(value = "添加", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "添加成功", response = String.class)
//    @PreAuthorize("hasPermission(Object target, Object permission)")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @Override
    public ResponseEntity<UserAccountDTO> create(@RequestBody UserAccountDTO dto) {
        if (!ObjectUtils.isEmpty(dto.getId())) {
            throw new BadRequestAlertException("A new " + entityName + " cannot already have an ID",
                    null, "id.exists", entityName);
        } else if (service.getByUsername(dto.getUsername()).isPresent()) {
            throw new UsernameAlreadyUsedException(entityName);
        } else if (service.getByMobile(dto.getMobile()).isPresent()) {
            throw new MobileAlreadyUsedException(entityName);
        } else if (service.getByEmail(dto.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException(entityName);
        } else {
            service.create(dto);
            return ResponseEntity.ok()
                    .headers(HeaderUtil.createEntityCreationAlert(entityName, dto.getAlertParam()))
                    .body(dto);
        }
    }

    @ApiOperation(value = "更新", httpMethod = "PUT", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "更新成功", response = String.class)
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<UserAccountDTO> modify(@PathVariable("id") Long id,
                                                 @RequestBody UserAccountDTO dto) {
        Optional<UserAccountDTO> optional = service.getByUsername(dto.getUsername());
        if (optional.isPresent() && optional.get().getId().equals(id)) {
            throw new UsernameAlreadyUsedException(entityName);
        }
        optional = service.getByMobile(dto.getMobile());
        if (optional.isPresent() && optional.get().getId().equals(id)) {
            throw new MobileAlreadyUsedException(entityName);
        }
        optional = service.getByEmail(dto.getEmail());
        if (optional.isPresent() && optional.get().getId().equals(id)) {
            throw new EmailAlreadyUsedException(entityName);
        }
        dto.setId(id);
        dto.setDeleted(DictConstant.BYTE_YES_NO_N);
        service.modify(dto);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(entityName, dto.getAlertParam()))
                .body(dto);
    }
}

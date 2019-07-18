package com.vinesmario.microservice.server.industry.web.rest;

import com.vinesmario.microservice.client.uaa.dto.UserAccountDto;
import com.vinesmario.microservice.client.uaa.dto.condition.UserAccountConditionDto;
import com.vinesmario.microservice.client.uaa.web.feign.UserAccountClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author
 * @date
 */

@Api(description = "UserAccountCRUD", tags = "UserAccountController", basePath = "/userAccounts")
@RestController
@RequestMapping("/api/v1/aaa")
public class AaaResource {

    @Autowired
    UserAccountClient userAccountClient;

    @ApiOperation(value = "查询列表，有分页参数则分页", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "查询成功", response = String.class)
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<List<UserAccountDto>> search(@ModelAttribute UserAccountConditionDto conditionDto) {
        return userAccountClient.search(conditionDto);
    }

    @ApiOperation(value = "查找明细", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "查询成功", response = String.class)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<UserAccountDto> get(@PathVariable("id") Long id) {
        return userAccountClient.get(id);
    }

}

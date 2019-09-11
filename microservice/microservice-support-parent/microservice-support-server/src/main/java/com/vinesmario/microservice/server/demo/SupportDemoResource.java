package com.vinesmario.microservice.server.demo;

import com.vinesmario.microservice.client.demo.*;
import com.vinesmario.microservice.server.security.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author
 * @date
 */

@Api(description = "DemoCRUD", tags = "DemoController", basePath = "/support_demo")
@Slf4j
@RestController
@RequestMapping("/api/v1/support_demo")
public class SupportDemoResource implements SupportDemoClient {

    @Autowired
    private UaaDemoClient uaaDemoClient;

    @ApiOperation(value = "查询列表，有分页参数则分页", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "查询成功", response = String.class)
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<List<SupportDemoDTO>> search(SupportDemoConditionDTO condition) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        log.info("AccessToken: " + ((OAuth2AuthenticationDetails) authentication.getDetails()).getTokenValue());
        UaaDemoConditionDTO conditionDTO = new UaaDemoConditionDTO();
        conditionDTO.setPageNumber(0);
        conditionDTO.setPageSize(10);
        uaaDemoClient.search(conditionDTO);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "查找明细", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "查询成功", response = String.class)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<SupportDemoDTO> get(Long id) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        log.info("AccessToken: " + ((OAuth2AuthenticationDetails) authentication.getDetails()).getTokenValue());
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "添加", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "添加成功", response = String.class)
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<SupportDemoDTO> create(SupportDemoDTO dto) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        log.info("AccessToken: " + ((OAuth2AuthenticationDetails) authentication.getDetails()).getTokenValue());
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "更新", httpMethod = "PUT", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "更新成功", response = String.class)
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<SupportDemoDTO> modify(Long id, SupportDemoDTO dto) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        log.info("AccessToken: " + ((OAuth2AuthenticationDetails) authentication.getDetails()).getTokenValue());
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "删除成功", response = String.class)
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<Void> remove(Long id) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        log.info("AccessToken: " + ((OAuth2AuthenticationDetails) authentication.getDetails()).getTokenValue());
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "批量删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "批量删除成功", response = String.class)
    @DeleteMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<Void> remove(SupportDemoConditionDTO conditionDTO) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        log.info("AccessToken: " + ((OAuth2AuthenticationDetails) authentication.getDetails()).getTokenValue());
        return ResponseEntity.ok().build();
    }

}

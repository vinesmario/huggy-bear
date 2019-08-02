package com.vinesmario.microservice.server.demo;

import com.vinesmario.microservice.client.common.security.ServiceTokenEndpointClient;
import com.vinesmario.microservice.client.demo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author
 * @date
 */
@Api(description = "ScheduleDemoCRUD", tags = "ScheduleDemoController", basePath = "/schedule_demo")
@Slf4j
@RestController
@RequestMapping("/api/v1/schedule_demo")
public class ScheduleDemoResource implements ScheduleDemoClient {

    @Autowired
    private UaaDemoClient uaaDemoClient;

    @ApiOperation(value = "查询列表，有分页参数则分页", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "查询成功", response = String.class)
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<List<ScheduleDemoDTO>> search(ScheduleDemoConditionDTO condition) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        log.info("AccessToken: " + ((OAuth2AuthenticationDetails) authentication.getDetails()).getTokenValue());
        UaaDemoConditionDTO conditionDTO = new UaaDemoConditionDTO();
        conditionDTO.setPageNumber(0);
        conditionDTO.setPageSize(10);
        // 坑①：在controller中接受并处理request请求的方法中，以feign的方式调用外部接口，并不会透传access_token
        uaaDemoClient.search(conditionDTO);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "查找明细", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "查询成功", response = String.class)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<ScheduleDemoDTO> get(Long id) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        log.info("AccessToken: " + ((OAuth2AuthenticationDetails) authentication.getDetails()).getTokenValue());
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "添加", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "添加成功", response = String.class)
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<ScheduleDemoDTO> create(ScheduleDemoDTO dto) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        log.info("AccessToken: " + ((OAuth2AuthenticationDetails) authentication.getDetails()).getTokenValue());
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "更新", httpMethod = "PUT", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "更新成功", response = String.class)
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<ScheduleDemoDTO> modify(Long id, ScheduleDemoDTO dto) {
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
    public ResponseEntity<Void> remove(ScheduleDemoConditionDTO conditionDTO) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        log.info("AccessToken: " + ((OAuth2AuthenticationDetails) authentication.getDetails()).getTokenValue());
        return ResponseEntity.ok().build();
    }

    @Autowired
    private ServiceTokenEndpointClient serviceTokenEndpointClient;
    @Autowired
    private OAuth2ClientContext oauth2ClientContext;

    /**
     * 启动时获取token
     * 并其定时重新获取，间隔时间要设置得小于access_token的过期时间
     */
    @PostConstruct
    @Scheduled(cron = "0 0/5 * * * ?")
    public void init() {
        OAuth2AccessToken accessToken = serviceTokenEndpointClient.sendClentCredentialsGrant();
        log.info("AccessToken: " + accessToken.getValue());
        oauth2ClientContext.setAccessToken(accessToken);
    }

    /**
     * 定时任务
     */
    @Scheduled(cron = "0 * * * * ?")
    public void job() {
        log.info("AccessToken: " + oauth2ClientContext.getAccessToken().getValue());
        UaaDemoConditionDTO conditionDTO = new UaaDemoConditionDTO();
        conditionDTO.setPageNumber(0);
        conditionDTO.setPageSize(10);
        ResponseEntity<List<UaaDemoDTO>> responseEntity = uaaDemoClient.search(conditionDTO);
        log.info("StatusCodeValue: " + responseEntity.getStatusCodeValue());
    }

}

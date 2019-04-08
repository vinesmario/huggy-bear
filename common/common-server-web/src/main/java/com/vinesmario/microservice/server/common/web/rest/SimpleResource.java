package com.vinesmario.microservice.server.common.web.rest;

import com.vinesmario.microservice.client.common.dto.BaseDto;
import com.vinesmario.microservice.client.common.dto.condition.ConditionDto;
import com.vinesmario.microservice.client.common.web.feign.RetrieveClient;
import com.vinesmario.microservice.server.common.service.RetrieveService;
import com.vinesmario.microservice.server.common.web.rest.util.PaginationUtil;
import com.vinesmario.microservice.server.common.web.rest.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public abstract class SimpleResource<DTO extends BaseDto, CONDITION extends ConditionDto, PK extends Serializable>
        implements RetrieveClient<DTO, CONDITION, PK> {

    private static final String DEFAULT_PROPERTY_DELIMITER = ",";
    private RetrieveService<DTO, PK> service;

    /**
     * 预处理查询条件
     *
     * @param conditionDto
     */
    public abstract void preConditionDto(CONDITION conditionDto);

    @ApiOperation(value = "查询列表，有分页参数则分页", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "查询成功", response = String.class)
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<List<DTO>> search(@ModelAttribute CONDITION conditionDto) {
        preConditionDto(conditionDto);
        // 创建分页对象PageRequest
        String[] directionParameter = conditionDto.getSort();
        Sort sort = null;
        if (!ObjectUtils.isEmpty(directionParameter)) {
            sort = parseParameterIntoSort(directionParameter, DEFAULT_PROPERTY_DELIMITER);
        }
        int a = 1 / 0;
        if (ObjectUtils.isEmpty(conditionDto.getPageNumber())
                || ObjectUtils.isEmpty(conditionDto.getPageSize())) {
            // 分页参数不全
            List<DTO> list = getService().list(conditionDto, sort);
            return ResponseEntity.ok().body(list);
        } else {
            Pageable pageable = PageRequest.of(conditionDto.getPageNumber(), conditionDto.getPageSize(), sort);
            Page<DTO> page = getService().page(conditionDto, pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/page");
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
    }

    @ApiOperation(value = "查找明细", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiResponse(code = 200, message = "查询成功", response = String.class)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<DTO> get(@PathVariable("id") PK id) {
        Optional<DTO> dto = getService().get(id);
        return ResponseUtil.wrapOrNotFound(dto);
    }

    /**
     * @param source
     * @param delimiter
     * @return
     * @see org.springframework.data.web.SortHandlerMethodArgumentResolver parseParameterIntoSort
     */
    private Sort parseParameterIntoSort(String[] source, String delimiter) {
        List<Sort.Order> allOrders = new ArrayList();
        String[] var4 = source;
        int var5 = source.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            String part = var4[var6];
            if (part != null) {
                String[] elements = part.split(delimiter);
                Optional<Sort.Direction> direction = elements.length == 0 ? Optional.empty() : Sort.Direction.fromOptionalString(elements[elements.length - 1]);
                int lastIndex = (Integer) direction.map((it) -> {
                    return elements.length - 1;
                }).orElseGet(() -> {
                    return elements.length;
                });

                for (int i = 0; i < lastIndex; ++i) {
                    toOrder(elements[i], direction).ifPresent(allOrders::add);
                }
            }
        }

        return allOrders.isEmpty() ? Sort.unsorted() : Sort.by(allOrders);
    }

    /**
     * @param property
     * @param direction
     * @return
     * @see org.springframework.data.web.SortHandlerMethodArgumentResolver toOrder
     */
    private Optional<Sort.Order> toOrder(String property, Optional<Sort.Direction> direction) {
        return !StringUtils.hasText(property) ? Optional.empty() : Optional.of(direction.map((it) -> {
            return new Sort.Order(it, property);
        }).orElseGet(() -> {
            return Sort.Order.by(property);
        }));
    }
}

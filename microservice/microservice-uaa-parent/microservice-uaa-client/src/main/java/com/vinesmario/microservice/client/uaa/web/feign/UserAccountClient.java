package com.vinesmario.microservice.client.uaa.web.feign;

import com.vinesmario.microservice.client.common.web.feign.CrudClient;
import com.vinesmario.microservice.client.uaa.dto.UserAccountDto;
import com.vinesmario.microservice.client.uaa.dto.condition.UserAccountConditionDto;
import com.vinesmario.microservice.client.uaa.web.hystrix.UserAccountFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Only single-level inheritance supported
 * 只支持一层继承
 *
 * @author
 * @date
 */
@FeignClient(name = "microservice-uaa-server", path = "/api/v1/user_account", fallbackFactory = UserAccountFallbackFactory.class)
public interface UserAccountClient extends CrudClient<UserAccountDto, UserAccountConditionDto, Long> {

    @GetMapping("")
    ResponseEntity<List<UserAccountDto>> search(@ModelAttribute UserAccountConditionDto condition);

    @GetMapping("/{id}")
    ResponseEntity<UserAccountDto> get(@PathVariable("id") Long id);

    @PostMapping("")
    ResponseEntity<UserAccountDto> create(@RequestBody UserAccountDto dto);

    @PutMapping("/{id}")
    ResponseEntity<UserAccountDto> modify(@PathVariable("id") Long id,
                                          @RequestBody UserAccountDto dto);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> remove(@PathVariable("id") Long id);

    @DeleteMapping("")
    ResponseEntity<Void> remove(@RequestBody UserAccountConditionDto conditionDto);

}

package com.vinesmario.microservice.client.uaa.web.feign;

import com.vinesmario.microservice.client.common.web.feign.CrudClient;
import com.vinesmario.microservice.client.uaa.dto.UserAccountDTO;
import com.vinesmario.microservice.client.uaa.dto.condition.UserAccountConditionDTO;
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
public interface UserAccountClient extends CrudClient<UserAccountDTO, UserAccountConditionDTO, Long> {

    @GetMapping("")
    ResponseEntity<List<UserAccountDTO>> search(@ModelAttribute UserAccountConditionDTO condition);

    @GetMapping("/{id}")
    ResponseEntity<UserAccountDTO> get(@PathVariable("id") Long id);

    @PostMapping("")
    ResponseEntity<UserAccountDTO> create(@RequestBody UserAccountDTO dto);

    @PutMapping("/{id}")
    ResponseEntity<UserAccountDTO> modify(@PathVariable("id") Long id,
                                          @RequestBody UserAccountDTO dto);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> remove(@PathVariable("id") Long id);

    @DeleteMapping("")
    ResponseEntity<Void> remove(@RequestBody UserAccountConditionDTO conditionDTO);

}

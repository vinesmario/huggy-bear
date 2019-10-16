package com.vinesmario.microservice.client.uaa.web.feign;

import com.vinesmario.microservice.client.common.web.feign.CrudClient;
import com.vinesmario.microservice.client.uaa.dto.OauthUserDTO;
import com.vinesmario.microservice.client.uaa.dto.condition.OauthUserConditionDTO;
import com.vinesmario.microservice.client.uaa.web.hystrix.OauthUserFallbackFactory;
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
@FeignClient(name = "microservice-uaa-server", path = "/api/v1/oauth_user", fallbackFactory = OauthUserFallbackFactory.class)
public interface OauthUserClient extends CrudClient<OauthUserDTO, OauthUserConditionDTO, Long> {

    @GetMapping("")
    ResponseEntity<List<OauthUserDTO>> search(@ModelAttribute OauthUserConditionDTO condition);

    @GetMapping("/{id}")
    ResponseEntity<OauthUserDTO> get(@PathVariable("id") Long id);

    @PostMapping("")
    ResponseEntity<OauthUserDTO> create(@RequestBody OauthUserDTO dto);

    @PutMapping("/{id}")
    ResponseEntity<OauthUserDTO> modify(@PathVariable("id") Long id,
                                          @RequestBody OauthUserDTO dto);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> remove(@PathVariable("id") Long id);

    @DeleteMapping("")
    ResponseEntity<Void> remove(@RequestBody OauthUserConditionDTO conditionDTO);

}

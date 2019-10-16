package com.vinesmario.microservice.client.uaa.web.hystrix;

import com.vinesmario.microservice.client.uaa.dto.OauthUserDTO;
import com.vinesmario.microservice.client.uaa.dto.condition.OauthUserConditionDTO;
import com.vinesmario.microservice.client.uaa.web.feign.OauthUserClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class OauthUserFallbackFactory implements FallbackFactory<OauthUserClient> {

    @Override
    public OauthUserClient create(Throwable throwable) {
        return new OauthUserClient() {
            @Override
            public ResponseEntity<List<OauthUserDTO>> search(OauthUserConditionDTO condition) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<OauthUserDTO> get(Long id) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<OauthUserDTO> create(OauthUserDTO dto) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<OauthUserDTO> modify(Long id, OauthUserDTO dto) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<Void> remove(Long id) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<Void> remove(OauthUserConditionDTO condition){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }
        };
    }
}

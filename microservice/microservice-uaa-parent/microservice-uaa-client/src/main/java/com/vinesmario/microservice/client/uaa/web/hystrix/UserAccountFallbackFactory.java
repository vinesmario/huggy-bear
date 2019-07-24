package com.vinesmario.microservice.client.uaa.web.hystrix;

import com.vinesmario.microservice.client.uaa.dto.UserAccountDto;
import com.vinesmario.microservice.client.uaa.dto.condition.UserAccountConditionDto;
import com.vinesmario.microservice.client.uaa.web.feign.UserAccountClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class UserAccountFallbackFactory implements FallbackFactory<UserAccountClient> {

    @Override
    public UserAccountClient create(Throwable throwable) {
        return new UserAccountClient() {
            @Override
            public ResponseEntity<List<UserAccountDto>> search(UserAccountConditionDto condition) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<UserAccountDto> get(Long id) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<UserAccountDto> create(UserAccountDto dto) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<UserAccountDto> modify(Long id, UserAccountDto dto) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<Void> remove(Long id) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<Void> remove(UserAccountConditionDto condition){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }
        };
    }
}

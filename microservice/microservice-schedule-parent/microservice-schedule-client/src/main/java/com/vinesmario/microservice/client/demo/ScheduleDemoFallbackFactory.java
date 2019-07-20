package com.vinesmario.microservice.client.demo;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author
 * @date
 */
@Slf4j
@Component
public class ScheduleDemoFallbackFactory implements FallbackFactory<ScheduleDemoClient>{

    @Override
    public ScheduleDemoClient create(Throwable throwable){
		return new ScheduleDemoClient() {
            @Override
			public ResponseEntity<List<ScheduleDemoDto>> search(ScheduleDemoConditionDto condition){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<ScheduleDemoDto>get(Long id){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<ScheduleDemoDto>create(ScheduleDemoDto dto){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<ScheduleDemoDto>modify(Long id, ScheduleDemoDto dto){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<Void> delete(Long id){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<Void> delete(ScheduleDemoConditionDto condition){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }
        };
    }
}

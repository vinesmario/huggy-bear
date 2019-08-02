<#include "/java_copyright.include">
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
public class ${basepackageCap}DemoFallbackFactory implements FallbackFactory<${basepackageCap}DemoClient>{

    @Override
    public ${basepackageCap}DemoClient create(Throwable throwable){
		return new ${basepackageCap}DemoClient() {
            @Override
			public ResponseEntity<List<${basepackageCap}DemoDTO>> search(${basepackageCap}DemoConditionDTO condition){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<${basepackageCap}DemoDTO>get(Long id){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<${basepackageCap}DemoDTO>create(${basepackageCap}DemoDTO dto){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<${basepackageCap}DemoDTO>modify(Long id, ${basepackageCap}DemoDTO dto){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<Void> remove(Long id){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<Void> remove(${basepackageCap}DemoConditionDTO condition){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }
        };
    }
}

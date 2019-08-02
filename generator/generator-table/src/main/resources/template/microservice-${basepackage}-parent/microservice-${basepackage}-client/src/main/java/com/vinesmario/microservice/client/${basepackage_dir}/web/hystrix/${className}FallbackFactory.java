<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package com.vinesmario.microservice.client.${basepackage}.web.client;

import com.vinesmario.microservice.client.${basepackage}.dto.${className}DTO;
import com.vinesmario.microservice.client.${basepackage}.dto.condition.${className}ConditionDTO;
import com.vinesmario.microservice.client.${basepackage}.web.feign.${className}Client;
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
public class ${className}FallbackFactory implements FallbackFactory<${className}Client>{

    @Override
    public ${className}Client create(Throwable throwable){
		return new ${className}Client() {
            @Override
			public ResponseEntity<List<${className}DTO>> search(${className}ConditionDTO condition){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<${className}DTO>get(Long id){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<${className}DTO>create(${className}DTO dto){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<${className}DTO>modify(Long id, ${className}DTO dto){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<Void> remove(Long id){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<Void> remove(${className}ConditionDTO condition){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }
        };
    }
}

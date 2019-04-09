<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package com.vinesmario.microservice.client.${basepackage}.web.client;

import com.vinesmario.microservice.client.common.web.feign.CrudClient;
{basepackage}.dto.${className}Dto;
import com.vinesmario.microservice.client.${basepackage}.dto.condition.${className}ConditionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author
 * @date
 */
@Slf4j
@Component
public interface ${className}FallbackFactory implements FallbackFactory<${className}Client>{

    @Override
    public ${className}Client create(Throwable throwable){
		return new ${className}Client() {
            @Override
			public ResponseEntity<List<${className}Dto>> search({className}ConditionDto condition){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<${className}Dto>get(Long id){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<${className}Dto>create(${className}Dto ${classNameLower}){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<${className}Dto>modify(Long id, ${className}Dto ${classNameLower}){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<Void> delete(Long id){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

        };
    }
}

package com.vinesmario.microservice.client.storage.web.hystrix;

import com.vinesmario.microservice.client.storage.dto.StorageImageDto;
import com.vinesmario.microservice.client.storage.dto.condition.StorageImageConditionDto;
import com.vinesmario.microservice.client.storage.web.feign.StorageImageClient;
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
public class StorageImageFallbackFactory implements FallbackFactory<StorageImageClient>{

    @Override
    public StorageImageClient create(Throwable throwable){
		return new StorageImageClient() {
            @Override
			public ResponseEntity<List<StorageImageDto>> search(StorageImageConditionDto condition){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StorageImageDto>get(Long id){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StorageImageDto>create(StorageImageDto storageImage){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StorageImageDto>modify(Long id, StorageImageDto storageImage){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<Void> delete(Long id){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<Void> deleteByExample(StorageImageConditionDto condition){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }
        };
    }
}

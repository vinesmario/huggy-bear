package com.vinesmario.microservice.client.storage.web.hystrix;

import com.vinesmario.microservice.client.storage.dto.StorageFileDto;
import com.vinesmario.microservice.client.storage.dto.condition.StorageFileConditionDto;
import com.vinesmario.microservice.client.storage.web.feign.StorageFileClient;
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
public class StorageFileFallbackFactory implements FallbackFactory<StorageFileClient>{

    @Override
    public StorageFileClient create(Throwable throwable){
		return new StorageFileClient() {
            @Override
			public ResponseEntity<List<StorageFileDto>> search(StorageFileConditionDto condition){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StorageFileDto>get(Long id){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StorageFileDto>create(StorageFileDto storageFile){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StorageFileDto>modify(Long id, StorageFileDto storageFile){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<Void> delete(Long id){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<Void> delete(StorageFileConditionDto condition){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }
        };
    }
}

package com.vinesmario.microservice.client.storage.web.hystrix;

import com.vinesmario.microservice.client.storage.dto.StorageExcelDto;
import com.vinesmario.microservice.client.storage.dto.StorageFileDto;
import com.vinesmario.microservice.client.storage.dto.condition.StorageFileConditionDto;
import com.vinesmario.microservice.client.storage.web.feign.StorageFileClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author
 * @date
 */
@Slf4j
@Component
public class StorageFileFallbackFactory implements FallbackFactory<StorageFileClient> {

    @Override
    public StorageFileClient create(Throwable throwable) {
        return new StorageFileClient() {
            @Override
            public ResponseEntity<List<StorageFileDto>> search(StorageFileConditionDto condition) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StorageFileDto> get(Long id) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StorageFileDto> create(StorageFileDto dto) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StorageFileDto> modify(Long id, StorageFileDto dto) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<Void> delete(Long id) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<Void> delete(StorageFileConditionDto condition) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StorageFileDto> upload(MultipartFile multipartFile,
                                                         Long tenantId, String memo) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<byte[]> download(String uuid) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

        };
    }
}

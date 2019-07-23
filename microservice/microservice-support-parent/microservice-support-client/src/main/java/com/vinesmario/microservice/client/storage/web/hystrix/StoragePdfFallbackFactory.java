package com.vinesmario.microservice.client.storage.web.hystrix;

import com.vinesmario.microservice.client.storage.dto.StorageExcelDto;
import com.vinesmario.microservice.client.storage.dto.StoragePdfDto;
import com.vinesmario.microservice.client.storage.dto.condition.StoragePdfConditionDto;
import com.vinesmario.microservice.client.storage.web.feign.StoragePdfClient;
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
public class StoragePdfFallbackFactory implements FallbackFactory<StoragePdfClient> {

    @Override
    public StoragePdfClient create(Throwable throwable) {
        return new StoragePdfClient() {
            @Override
            public ResponseEntity<List<StoragePdfDto>> search(StoragePdfConditionDto condition) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StoragePdfDto> get(Long id) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StoragePdfDto> create(StoragePdfDto dto) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StoragePdfDto> modify(Long id, StoragePdfDto dto) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<Void> delete(Long id) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<Void> delete(StoragePdfConditionDto condition) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StoragePdfDto> upload(MultipartFile multipartFile,
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

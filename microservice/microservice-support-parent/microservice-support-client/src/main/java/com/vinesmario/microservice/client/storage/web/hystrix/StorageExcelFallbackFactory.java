package com.vinesmario.microservice.client.storage.web.hystrix;

import com.vinesmario.microservice.client.storage.dto.StorageExcelDto;
import com.vinesmario.microservice.client.storage.dto.condition.StorageExcelConditionDto;
import com.vinesmario.microservice.client.storage.web.feign.StorageExcelClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author
 * @date
 */
@Slf4j
@Component
public class StorageExcelFallbackFactory implements FallbackFactory<StorageExcelClient> {

    @Override
    public StorageExcelClient create(Throwable throwable) {
        return new StorageExcelClient() {
            @Override
            public ResponseEntity<List<StorageExcelDto>> search(StorageExcelConditionDto condition) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StorageExcelDto> get(Long id) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StorageExcelDto> create(StorageExcelDto storageExcel) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StorageExcelDto> modify(Long id, StorageExcelDto storageExcel) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<Void> remove(Long id) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<Void> remove(StorageExcelConditionDto condition) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StorageExcelDto> upload(MultipartFile multipartFile,
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

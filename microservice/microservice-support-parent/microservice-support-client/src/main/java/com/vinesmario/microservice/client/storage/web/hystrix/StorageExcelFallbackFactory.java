package com.vinesmario.microservice.client.storage.web.hystrix;

import com.vinesmario.microservice.client.storage.dto.StorageExcelDTO;
import com.vinesmario.microservice.client.storage.dto.condition.StorageExcelConditionDTO;
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
            public ResponseEntity<List<StorageExcelDTO>> search(StorageExcelConditionDTO condition) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StorageExcelDTO> get(Long id) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StorageExcelDTO> create(StorageExcelDTO storageExcel) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StorageExcelDTO> modify(Long id, StorageExcelDTO storageExcel) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<Void> remove(Long id) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<Void> remove(StorageExcelConditionDTO condition) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StorageExcelDTO> upload(MultipartFile multipartFile,
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

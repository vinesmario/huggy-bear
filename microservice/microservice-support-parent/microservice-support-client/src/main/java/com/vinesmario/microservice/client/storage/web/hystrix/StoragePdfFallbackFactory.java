package com.vinesmario.microservice.client.storage.web.hystrix;

import com.vinesmario.microservice.client.storage.dto.StorageExcelDTO;
import com.vinesmario.microservice.client.storage.dto.StoragePdfDTO;
import com.vinesmario.microservice.client.storage.dto.condition.StoragePdfConditionDTO;
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
            public ResponseEntity<List<StoragePdfDTO>> search(StoragePdfConditionDTO condition) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StoragePdfDTO> get(Long id) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StoragePdfDTO> create(StoragePdfDTO dto) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StoragePdfDTO> modify(Long id, StoragePdfDTO dto) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<Void> remove(Long id) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<Void> remove(StoragePdfConditionDTO condition) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StoragePdfDTO> upload(MultipartFile multipartFile,
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

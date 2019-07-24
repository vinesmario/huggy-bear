package com.vinesmario.microservice.client.storage.web.hystrix;

import com.vinesmario.microservice.client.storage.dto.StoragePdfSplitedDto;
import com.vinesmario.microservice.client.storage.dto.condition.StoragePdfSplitedConditionDto;
import com.vinesmario.microservice.client.storage.web.feign.StoragePdfSplitedClient;
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
public class StoragePdfSplitedFallbackFactory implements FallbackFactory<StoragePdfSplitedClient> {

    @Override
    public StoragePdfSplitedClient create(Throwable throwable) {
        return new StoragePdfSplitedClient() {
            @Override
            public ResponseEntity<List<StoragePdfSplitedDto>> search(StoragePdfSplitedConditionDto condition) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StoragePdfSplitedDto> get(Long id) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StoragePdfSplitedDto> create(StoragePdfSplitedDto dto) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StoragePdfSplitedDto> modify(Long id, StoragePdfSplitedDto dto) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<Void> remove(Long id) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<Void> remove(StoragePdfSplitedConditionDto condition) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StoragePdfSplitedDto> upload(MultipartFile multipartFile,
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

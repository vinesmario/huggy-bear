package com.vinesmario.microservice.client.storage.web.hystrix;

import com.vinesmario.microservice.client.storage.dto.StoragePdfSplitedDto;
import com.vinesmario.microservice.client.storage.dto.StorageTemplateDto;
import com.vinesmario.microservice.client.storage.dto.condition.StorageTemplateConditionDto;
import com.vinesmario.microservice.client.storage.web.feign.StorageTemplateClient;
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
public class StorageTemplateFallbackFactory implements FallbackFactory<StorageTemplateClient> {

    @Override
    public StorageTemplateClient create(Throwable throwable) {
        return new StorageTemplateClient() {
            @Override
            public ResponseEntity<List<StorageTemplateDto>> search(StorageTemplateConditionDto condition) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StorageTemplateDto> get(Long id) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StorageTemplateDto> create(StorageTemplateDto dto) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StorageTemplateDto> modify(Long id, StorageTemplateDto dto) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<Void> delete(Long id) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<Void> delete(StorageTemplateConditionDto condition) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StorageTemplateDto> upload(MultipartFile multipartFile,
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

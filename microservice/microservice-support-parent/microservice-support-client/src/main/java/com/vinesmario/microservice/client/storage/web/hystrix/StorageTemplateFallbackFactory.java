package com.vinesmario.microservice.client.storage.web.hystrix;

import com.vinesmario.microservice.client.storage.dto.StoragePdfSplitedDTO;
import com.vinesmario.microservice.client.storage.dto.StorageTemplateDTO;
import com.vinesmario.microservice.client.storage.dto.condition.StorageTemplateConditionDTO;
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
            public ResponseEntity<List<StorageTemplateDTO>> search(StorageTemplateConditionDTO condition) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StorageTemplateDTO> get(Long id) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StorageTemplateDTO> create(StorageTemplateDTO dto) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StorageTemplateDTO> modify(Long id, StorageTemplateDTO dto) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<Void> remove(Long id) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<Void> remove(StorageTemplateConditionDTO condition) {
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<StorageTemplateDTO> upload(MultipartFile multipartFile,
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

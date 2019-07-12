package com.vinesmario.microservice.client.file.web.hystrix;

import com.vinesmario.microservice.client.file.dto.FileImageDto;
import com.vinesmario.microservice.client.file.dto.condition.FileImageConditionDto;
import com.vinesmario.microservice.client.file.web.feign.FileImageClient;
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
public class FileImageFallbackFactory implements FallbackFactory<FileImageClient>{

    @Override
    public FileImageClient create(Throwable throwable){
		return new FileImageClient() {
            @Override
			public ResponseEntity<List<FileImageDto>> search(FileImageConditionDto condition){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<FileImageDto>get(Long id){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<FileImageDto>create(FileImageDto fileImage){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<FileImageDto>modify(Long id, FileImageDto fileImage){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

            @Override
            public ResponseEntity<Void> delete(Long id){
                log.error("进入回退逻辑", throwable);
                return ResponseEntity.notFound().build();
            }

        };
    }
}

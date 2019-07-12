package com.vinesmario.microservice.client.file.web.feign;

import com.vinesmario.microservice.client.common.web.feign.CrudClient;
import com.vinesmario.microservice.client.file.dto.FileImageDto;
import com.vinesmario.microservice.client.file.dto.condition.FileImageConditionDto;
import com.vinesmario.microservice.client.file.web.hystrix.FileImageFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Only single-level inheritance supported
 * 只支持一层继承
 *
 * @author
 * @date
 */
@FeignClient(name = "microservice-file-server", path = "/api/v1/file_image", fallbackFactory = FileImageFallbackFactory.class)
public interface FileImageClient extends CrudClient<FileImageDto, FileImageConditionDto, Long>{

	@GetMapping("")
	ResponseEntity<List<FileImageDto>> search(@ModelAttribute FileImageConditionDto condition);

	@GetMapping("/{id}")
	ResponseEntity<FileImageDto> get(@PathVariable("id") Long id);

	@PostMapping("")
	ResponseEntity<FileImageDto> create(@RequestBody FileImageDto fileImage);

	@PutMapping("/{id}")
	ResponseEntity<FileImageDto> modify(@PathVariable("id") Long id, @RequestBody FileImageDto fileImage);

	@DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable("id") Long id);
	
}

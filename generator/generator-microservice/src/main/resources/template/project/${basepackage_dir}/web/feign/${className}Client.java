<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.web.client;

import com.cwgj.common.model.client.CrudClient;
import ${basepackage}.dto.${className}Dto;
import ${basepackage}.dto.condition.${className}ConditionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author
 * @date
 */
@FeignClient(name = "uaa-service", path = "/api/v1/${table.sqlName}")
public interface ${className}Client extends CrudClient<${className}Dto, ${className}ConditionDto, Long>{

	@GetMapping("/page")
	ResponseEntity<Page<${className}Dto>> page(@ModelAttribute ${className}ConditionDto condition,
												Pageable pageable);

	@GetMapping("/list")
	ResponseEntity<List<${className}Dto>> list(@ModelAttribute ${className}ConditionDto condition,
												Sort sort);

	@GetMapping("/{id}")
	ResponseEntity<${className}Dto> get(@PathVariable("id") Long id);

	@PostMapping("")
	ResponseEntity<${className}Dto> add(@RequestBody ${className}Dto ${classNameLower});

	@PutMapping("/{id}")
	ResponseEntity<${className}Dto> update(@PathVariable("id") Long id,
                           					@RequestBody ${className}Dto ${classNameLower});

	@DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable("id") Long id);
	
}

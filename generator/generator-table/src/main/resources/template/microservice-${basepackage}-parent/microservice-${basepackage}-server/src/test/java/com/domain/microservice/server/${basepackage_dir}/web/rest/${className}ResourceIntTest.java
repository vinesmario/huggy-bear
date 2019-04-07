<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package com.vinesmario.microservice.server.${basepackage}.web.rest;

import com.vinesmario.microservice.client.${basepackage}.dto.${className}Dto;
import com.vinesmario.microservice.server.common.constant.DictConstant;
import com.vinesmario.microservice.server.common.web.rest.TestUtil;
import com.vinesmario.microservice.server.common.web.rest.errors.ExceptionTranslator;
import com.vinesmario.microservice.server.${basepackage}.ServiceApplicationIntTest;
{basepackage}.entity.${className};
{basepackage}.mapstruct.${className}MapStructImpl;
{basepackage}.service.${className}Service;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ${className}Resource REST controller.
 *
 * @see ${className}Resource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceApplicationIntTest.class)
public class ${className}ResourceIntTest {

    private static final Long DEFAULT_ID = 1L;
    private static final String DEFAULT_USERNAME = "johndoe";
    private static final String DEFAULT_EMAIL = "johndoe@localhost";
    private static final String DEFAULT_MOBILE = "12345678901";
    private static final Long DEFAULT_CREATE_BY = 1L;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc rest${className}MockMvc;

    @Autowired
    private ${className}Resource ${classNameLower}Resource;
    @Autowired
    private ${className}Service ${classNameLower}Service;
    @Autowired
    private ${className}MapStructImpl ${classNameLower}MapStruct;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.rest${className}MockMvc = MockMvcBuilders.standaloneSetup(${classNameLower}Resource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter)
                .build();
    }

    /**
     * Create a object
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which has a required relationship to the entity.
     */
    public static ${className}Dto create() {
        ${className}Dto ${classNameLower}Dto = new ${className}Dto();
        <#list table.columns as column>
            <#if column.columnNameLower != 'id'
                    && column.columnNameLower != 'createdBy'
                    && column.columnNameLower != 'createdDate'
                    && column.columnNameLower != 'lastModifiedBy'
                    && column.columnNameLower != 'lastModifiedDate'
                    && column.columnNameLower != 'memo'
                    && column.columnNameLower != 'deleted'>
                <#if column.simpleJavaType == 'String'>
        ${classNameLower}Dto.set${column.columnName}(RandomStringUtils.randomAlphabetic(5));
                <#elseif column.simpleJavaType == 'Long'>
        ${classNameLower}Dto.set${column.columnName}(Long.parseLong(RandomStringUtils.randomNumeric(8)));
                <#elseif column.simpleJavaType == 'Integer'>
        ${classNameLower}Dto.set${column.columnName}(Integer.parseInt(RandomStringUtils.randomNumeric(4)));
                <#elseif column.simpleJavaType == 'Byte'>
        ${classNameLower}Dto.set${column.columnName}(DictConstant.BYTE_YES_NO_N);
                <#elseif column.simpleJavaType == 'BigDecimal'>
        ${classNameLower}Dto.set${column.columnName}(BigDecimal.TEN);
                <#elseif column.simpleJavaType == 'LocalDateTime'>
        ${classNameLower}Dto.set${column.columnName}(LocalDateTime.now());
                <#elseif column.simpleJavaType == 'LocalDate'>
        ${classNameLower}Dto.set${column.columnName}(LocalDate.now());
                <#elseif column.simpleJavaType == 'LocalTime'>
        ${classNameLower}Dto.set${column.columnName}(LocalTime.now());
                <#else>
                </#if>
            </#if>
        </#list>
        return ${classNameLower}Dto;
    }

    @Test
    @Transactional
    public void testCreate() throws Exception {
        int databaseSizeBeforeCreate = ${classNameLower}Service.list(null).size();

        // Create the object
        ${className}Dto oneDto = create();
        rest${className}MockMvc.perform(post("/api/v1/${table.sqlName}")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oneDto)))
                .andExpect(status().isOk());

        // Validate the User in the database
        List<${className}Dto> ${classNameLower}DtoList = ${classNameLower}Service.list(null);
        assertThat(${classNameLower}DtoList).hasSize(databaseSizeBeforeCreate + 1);
        ${className}Dto testDto = ${classNameLower}DtoList.get(${classNameLower}DtoList.size() - 1);
        <#list table.columns as column>
            <#if column.columnNameLower != 'id'
                    && column.columnNameLower != 'createdBy'
                    && column.columnNameLower != 'createdDate'
                    && column.columnNameLower != 'lastModifiedBy'
                    && column.columnNameLower != 'lastModifiedDate'
                    && column.columnNameLower != 'memo'
                    && column.columnNameLower != 'deleted'>
        assertThat(testDto.get${column.columnName}()).isEqualTo(oneDto.get${column.columnName}());
            </#if>
        </#list>
    }

    @Test
    @Transactional
    public void testCreateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ${classNameLower}Service.list(null).size();

        ${className}Dto oneDto = create();
        oneDto.setId(DEFAULT_ID);

        // An entity with an existing ID cannot be created, so this API call must fail
        rest${className}MockMvc.perform(post("/api/v1/${table.sqlName}")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oneDto)))
                .andExpect(status().isBadRequest());

        // Validate the object in the database
        List<${className}Dto> ${classNameLower}DtoList = ${classNameLower}Service.list(null);
        assertThat(${classNameLower}DtoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void testSearch() throws Exception {
        // Initialize the database
        ${className}Dto oneDto = create();
        ${classNameLower}Service.create(oneDto);

        // Get all the objects
        rest${className}MockMvc.perform(get("/api/v1/${table.sqlName}?sort=id,desc")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        <#list table.columns as column>
            <#if column.columnNameLower != 'id'
                    && column.columnNameLower != 'createdBy'
                    && column.columnNameLower != 'createdDate'
                    && column.columnNameLower != 'lastModifiedBy'
                    && column.columnNameLower != 'lastModifiedDate'
                    && column.columnNameLower != 'memo'
                    && column.columnNameLower != 'deleted'>
                <#if column.simpleJavaType == 'Byte'>
                .andExpect(jsonPath("$.[*].${column.columnNameLower}").value(hasItem(Integer.valueOf(oneDto.get${column.columnName}()))))
                <#else>
                .andExpect(jsonPath("$.[*].${column.columnNameLower}").value(hasItem(oneDto.get${column.columnName}())))
                </#if>
            </#if>
        </#list>
                ;
    }

    @Test
    @Transactional
    public void testGet() throws Exception {
        // Initialize the database
        ${className}Dto oneDto = create();
        ${classNameLower}Service.create(oneDto);

        // Get the object
        rest${className}MockMvc.perform(get("/api/v1/${table.sqlName}/{id}", oneDto.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        <#list table.columns as column>
            <#if column.columnNameLower != 'id'
                    && column.columnNameLower != 'createdBy'
                    && column.columnNameLower != 'createdDate'
                    && column.columnNameLower != 'lastModifiedBy'
                    && column.columnNameLower != 'lastModifiedDate'
                    && column.columnNameLower != 'memo'
                    && column.columnNameLower != 'deleted'>
                <#if column.simpleJavaType == 'Byte'>
                .andExpect(jsonPath("$.${column.columnNameLower}").value(Integer.valueOf(oneDto.get${column.columnName}())))
                <#else>
                .andExpect(jsonPath("$.${column.columnNameLower}").value(oneDto.get${column.columnName}()))
                </#if>
            </#if>
        </#list>
                ;
    }

    @Test
    @Transactional
    public void testGetNonExisting() throws Exception {
        rest${className}MockMvc.perform(get("/api/v1/${table.sqlName}/{id}", -1))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void testModify() throws Exception {
        // Initialize the database
        ${className}Dto oneDto = create();
        ${classNameLower}Service.create(oneDto);
        int databaseSizeBeforeUpdate = ${classNameLower}Service.list(null).size();

        // Update the object
        ${className}Dto updatedDto = ${classNameLower}Service.get(oneDto.getId()).get();

        ${className}Dto ${classNameLower}Dto = new ${className}Dto();
        ${classNameLower}Dto.setId(updatedDto.getId());
        <#list table.columns as column>
            <#if column.columnNameLower != 'id'
                    && column.columnNameLower != 'createdBy'
                    && column.columnNameLower != 'createdDate'
                    && column.columnNameLower != 'lastModifiedBy'
                    && column.columnNameLower != 'lastModifiedDate'
                    && column.columnNameLower != 'memo'
                    && column.columnNameLower != 'deleted'>
                <#if column.simpleJavaType == 'String'>
        ${classNameLower}Dto.set${column.columnName}(RandomStringUtils.randomAlphabetic(5));
                <#elseif column.simpleJavaType == 'Long'>
        ${classNameLower}Dto.set${column.columnName}(Long.parseLong(RandomStringUtils.randomNumeric(8)));
                <#elseif column.simpleJavaType == 'Integer'>
        ${classNameLower}Dto.set${column.columnName}(Integer.parseInt(RandomStringUtils.randomNumeric(4)));
                <#elseif column.simpleJavaType == 'Byte'>
        ${classNameLower}Dto.set${column.columnName}(DictConstant.BYTE_YES_NO_N);
                <#elseif column.simpleJavaType == 'BigDecimal'>
        ${classNameLower}Dto.set${column.columnName}(BigDecimal.TEN);
                <#elseif column.simpleJavaType == 'LocalDateTime'>
        ${classNameLower}Dto.set${column.columnName}(LocalDateTime.now());
                <#elseif column.simpleJavaType == 'LocalDate'>
        ${classNameLower}Dto.set${column.columnName}(LocalDate.now());
                <#elseif column.simpleJavaType == 'LocalTime'>
        ${classNameLower}Dto.set${column.columnName}(LocalTime.now());
                <#else>
                </#if>
            </#if>
        </#list>
        ${classNameLower}Dto.setCreatedBy(updatedDto.getCreatedBy());
        ${classNameLower}Dto.setLastModifiedBy(updatedDto.getLastModifiedBy());
        ${classNameLower}Dto.setDeleted(DictConstant.BYTE_YES_NO_N);

        rest${className}MockMvc.perform(put("/api/v1/${table.sqlName}/{id}", ${classNameLower}Dto.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(${classNameLower}Dto)))
                .andExpect(status().isOk());

        // Validate the object in the database
        List<${className}Dto> userList = ${classNameLower}Service.list(null);
        assertThat(userList).hasSize(databaseSizeBeforeUpdate);
        ${className}Dto testDto = userList.get(userList.size() - 1);
        <#list table.columns as column>
            <#if column.columnNameLower != 'id'
                    && column.columnNameLower != 'createdBy'
                    && column.columnNameLower != 'createdDate'
                    && column.columnNameLower != 'lastModifiedBy'
                    && column.columnNameLower != 'lastModifiedDate'
                    && column.columnNameLower != 'memo'
                    && column.columnNameLower != 'deleted'>
        assertThat(testDto.get${column.columnName}()).isEqualTo(${classNameLower}Dto.get${column.columnName}());
            </#if>
        </#list>
    }

    @Test
    @Transactional
    public void testDelete() throws Exception {
        // Initialize the database
        ${className}Dto oneDto = create();
        ${classNameLower}Service.create(oneDto);
        int databaseSizeBeforeDelete = ${classNameLower}Service.list(null).size();

        // Delete the object
        rest${className}MockMvc.perform(delete("/api/v1/${table.sqlName}/{id}", oneDto.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<${className}Dto> userList = ${classNameLower}Service.list(null);
        assertThat(userList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void testEquals() throws Exception {
        TestUtil.equalsVerifier(${className}Dto.class);
        ${className}Dto user1 = new ${className}Dto();
        user1.setUsername(DEFAULT_USERNAME + RandomStringUtils.randomAlphabetic(5));
        ${className}Dto user2 = new ${className}Dto();
        user2.setUsername(user1.getUsername());
        assertThat(user1).isEqualTo(user2);
        user2.setUsername(DEFAULT_USERNAME + RandomStringUtils.randomAlphabetic(5));
        assertThat(user1).isNotEqualTo(user2);
        user1.setUsername(null);
        assertThat(user1).isNotEqualTo(user2);
    }

    @Test
    public void testDto2Entity() {
        ${className}Dto dto = new ${className}Dto();
        <#list table.columns as column>
            <#if column.simpleJavaType == 'String'>
        dto.set${column.columnName}(RandomStringUtils.randomAlphabetic(5));
            <#elseif column.simpleJavaType == 'Long'>
        dto.set${column.columnName}(Long.parseLong(RandomStringUtils.randomNumeric(8)));
            <#elseif column.simpleJavaType == 'Integer'>
        dto.set${column.columnName}(Integer.parseInt(RandomStringUtils.randomNumeric(4)));
            <#elseif column.simpleJavaType == 'Byte'>
        dto.set${column.columnName}(DictConstant.BYTE_YES_NO_N);
            <#elseif column.simpleJavaType == 'BigDecimal'>
        dto.set${column.columnName}(BigDecimal.TEN);
            <#elseif column.simpleJavaType == 'LocalDateTime'>
        dto.set${column.columnName}(LocalDateTime.now());
            <#elseif column.simpleJavaType == 'LocalDate'>
        dto.set${column.columnName}(LocalDate.now());
            <#elseif column.simpleJavaType == 'LocalTime'>
        dto.set${column.columnName}(LocalTime.now());
            <#else>
            </#if>
        </#list>

        ${className} entity = ${classNameLower}MapStruct.fromDto2Entity(dto);

        <#list table.columns as column>
        assertThat(entity.get${column.columnName}()).isEqualTo(dto.get${column.columnName}());
        </#list>
        assertThat(entity.toString()).isNotNull();
    }

    @Test
    public void testEntityToDto() {
        ${className} entity = new ${className}();
        <#list table.columns as column>
            <#if column.simpleJavaType == 'String'>
        entity.set${column.columnName}(RandomStringUtils.randomAlphabetic(5));
            <#elseif column.simpleJavaType == 'Long'>
        entity.set${column.columnName}(Long.parseLong(RandomStringUtils.randomNumeric(8)));
            <#elseif column.simpleJavaType == 'Integer'>
        entity.set${column.columnName}(Integer.parseInt(RandomStringUtils.randomNumeric(4)));
            <#elseif column.simpleJavaType == 'Byte'>
        entity.set${column.columnName}(DictConstant.BYTE_YES_NO_N);
            <#elseif column.simpleJavaType == 'BigDecimal'>
        entity.set${column.columnName}(BigDecimal.TEN);
            <#elseif column.simpleJavaType == 'LocalDateTime'>
        entity.set${column.columnName}(LocalDateTime.now());
            <#elseif column.simpleJavaType == 'LocalDate'>
        entity.set${column.columnName}(LocalDate.now());
            <#elseif column.simpleJavaType == 'LocalTime'>
        entity.set${column.columnName}(LocalTime.now());
            <#else>
            </#if>
        </#list>

        ${className}Dto dto = ${classNameLower}MapStruct.fromEntity2Dto(entity);

        <#list table.columns as column>
        assertThat(dto.get${column.columnName}()).isEqualTo(entity.get${column.columnName}());
        </#list>
        assertThat(dto.toString()).isNotNull();
    }

}

package com.vinesmario.microservice.server.uaa.web.rest;

import com.vinesmario.microservice.client.uaa.dto.UserAccountDto;
import com.vinesmario.microservice.server.common.constant.DictConstant;
import com.vinesmario.microservice.server.common.web.rest.TestUtil;
import com.vinesmario.microservice.server.common.web.rest.errors.ExceptionTranslator;
import com.vinesmario.microservice.server.uaa.UaaServiceApplicationIntTest;
import com.vinesmario.microservice.server.uaa.entity.UserAccount;
import com.vinesmario.microservice.server.uaa.mapstruct.UserAccountMapStructImpl;
import com.vinesmario.microservice.server.uaa.service.UserAccountService;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserAccountResource REST controller.
 *
 * @see UserAccountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UaaServiceApplicationIntTest.class)
public class UserAccountResourceIntTest {

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

    private MockMvc restUserAccountMockMvc;

    @Autowired
    private UserAccountResource userAccountResource;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private UserAccountMapStructImpl userAccountMapStruct;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.restUserAccountMockMvc = MockMvcBuilders.standaloneSetup(userAccountResource)
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
    public static UserAccountDto create() {
        UserAccountDto userAccountDto = new UserAccountDto();
        userAccountDto.setUsername(RandomStringUtils.randomAlphabetic(5));
        userAccountDto.setPassword(RandomStringUtils.randomAlphabetic(5));
        userAccountDto.setMobile(RandomStringUtils.randomAlphabetic(5));
        userAccountDto.setEmail(RandomStringUtils.randomAlphabetic(5));
        userAccountDto.setActivated(DictConstant.BYTE_YES_NO_N);
        return userAccountDto;
    }

    @Test
    @Transactional
    public void testCreate() throws Exception {
        int databaseSizeBeforeCreate = userAccountService.list(null).size();

        // Create the object
        UserAccountDto oneDto = create();
        restUserAccountMockMvc.perform(post("/api/v1/user_account")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oneDto)))
                .andExpect(status().isOk());

        // Validate the User in the database
        List<UserAccountDto> userAccountDtoList = userAccountService.list(null);
        assertThat(userAccountDtoList).hasSize(databaseSizeBeforeCreate + 1);
        UserAccountDto testDto = userAccountDtoList.get(userAccountDtoList.size() - 1);
        assertThat(testDto.getUsername()).isEqualTo(oneDto.getUsername());
        assertThat(testDto.getPassword()).isEqualTo(oneDto.getPassword());
        assertThat(testDto.getMobile()).isEqualTo(oneDto.getMobile());
        assertThat(testDto.getEmail()).isEqualTo(oneDto.getEmail());
        assertThat(testDto.getActivated()).isEqualTo(oneDto.getActivated());
    }

    @Test
    @Transactional
    public void testCreateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userAccountService.list(null).size();

        UserAccountDto oneDto = create();
        oneDto.setId(DEFAULT_ID);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserAccountMockMvc.perform(post("/api/v1/user_account")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oneDto)))
                .andExpect(status().isBadRequest());

        // Validate the object in the database
        List<UserAccountDto> userAccountDtoList = userAccountService.list(null);
        assertThat(userAccountDtoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void testCreateWithExistingUsername() throws Exception {
        // Initialize the database
        UserAccountDto oneDto = create();
        userAccountService.create(oneDto);
        int databaseSizeBeforeCreate = userAccountService.list(null).size();

        UserAccountDto userAccountDto = new UserAccountDto();
        userAccountDto.setUsername(oneDto.getUsername());// this username should already be used
        userAccountDto.setPassword(RandomStringUtils.random(64));
        userAccountDto.setActivated(DictConstant.BYTE_YES_NO_N);

        // Create the object
        restUserAccountMockMvc.perform(post("/api/v1/user_account")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userAccountDto)))
                .andExpect(status().isBadRequest());

        // Validate the object in the database
        List<UserAccountDto> userList = userAccountService.list(null);
        assertThat(userList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void testCreateWithExistingMobile() throws Exception {
        // Initialize the database
        UserAccountDto oneDto = create();
        userAccountService.create(oneDto);
        int databaseSizeBeforeCreate = userAccountService.list(null).size();

        UserAccountDto userAccountDto = new UserAccountDto();
        userAccountDto.setUsername(DEFAULT_USERNAME + RandomStringUtils.randomAlphabetic(5));
        userAccountDto.setPassword(RandomStringUtils.random(64));
        userAccountDto.setMobile(oneDto.getMobile());// this mobile should already be used
        userAccountDto.setActivated(DictConstant.BYTE_YES_NO_N);

        // Create the object
        restUserAccountMockMvc.perform(post("/api/v1/user_account")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userAccountDto)))
                .andExpect(status().isBadRequest());

        // Validate the object in the database
        List<UserAccountDto> userList = userAccountService.list(null);
        assertThat(userList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void testCreateWithExistingEmail() throws Exception {
        // Initialize the database
        UserAccountDto oneDto = create();
        userAccountService.create(oneDto);
        int databaseSizeBeforeCreate = userAccountService.list(null).size();

        UserAccountDto userAccountDto = new UserAccountDto();
        userAccountDto.setUsername(DEFAULT_USERNAME + RandomStringUtils.randomAlphabetic(5));
        userAccountDto.setPassword(RandomStringUtils.random(64));
        userAccountDto.setEmail(oneDto.getEmail());// this email should already be used
        userAccountDto.setActivated(DictConstant.BYTE_YES_NO_N);

        // Create the object
        restUserAccountMockMvc.perform(post("/api/v1/user_account")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userAccountDto)))
                .andExpect(status().isBadRequest());

        // Validate the object in the database
        List<UserAccountDto> userAccountDtoList = userAccountService.list(null);
        assertThat(userAccountDtoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void testSearch() throws Exception {
        // Initialize the database
        UserAccountDto oneDto = create();
        userAccountService.create(oneDto);

        // Get all the objects
        restUserAccountMockMvc.perform(get("/api/v1/user_account")
                .param("pageNumber", "1")
                .param("pageSize", "1")
                .param("sort", "id,desc")
                .param("sort", "username,desc")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].username").value(hasItem(oneDto.getUsername())))
                .andExpect(jsonPath("$.[*].password").value(hasItem(oneDto.getPassword())))
                .andExpect(jsonPath("$.[*].mobile").value(hasItem(oneDto.getMobile())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(oneDto.getEmail())))
                .andExpect(jsonPath("$.[*].activated").value(hasItem(Integer.valueOf(oneDto.getActivated()))))
        ;
    }

    @Test
    @Transactional
    public void testGet() throws Exception {
        // Initialize the database
        UserAccountDto oneDto = create();
        userAccountService.create(oneDto);

        // Get the object
        restUserAccountMockMvc.perform(get("/api/v1/user_account/{id}", oneDto.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.username").value(oneDto.getUsername()))
                .andExpect(jsonPath("$.password").value(oneDto.getPassword()))
                .andExpect(jsonPath("$.mobile").value(oneDto.getMobile()))
                .andExpect(jsonPath("$.email").value(oneDto.getEmail()))
                .andExpect(jsonPath("$.activated").value(Integer.valueOf(oneDto.getActivated())))
        ;
    }

    @Test
    @Transactional
    public void testGetNonExisting() throws Exception {
        restUserAccountMockMvc.perform(get("/api/v1/user_account/{id}", -1))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void testModify() throws Exception {
        // Initialize the database
        UserAccountDto oneDto = create();
        userAccountService.create(oneDto);
        int databaseSizeBeforeUpdate = userAccountService.list(null).size();

        // Update the object
        UserAccountDto updatedDto = userAccountService.get(oneDto.getId()).get();

        UserAccountDto userAccountDto = new UserAccountDto();
        userAccountDto.setId(updatedDto.getId());
        userAccountDto.setUsername(RandomStringUtils.randomAlphabetic(5));
        userAccountDto.setPassword(RandomStringUtils.randomAlphabetic(5));
        userAccountDto.setMobile(RandomStringUtils.randomAlphabetic(5));
        userAccountDto.setEmail(RandomStringUtils.randomAlphabetic(5));
        userAccountDto.setActivated(DictConstant.BYTE_YES_NO_N);
        userAccountDto.setCreatedBy(updatedDto.getCreatedBy());
        userAccountDto.setLastModifiedBy(updatedDto.getLastModifiedBy());
        userAccountDto.setDeleted(DictConstant.BYTE_YES_NO_N);

        restUserAccountMockMvc.perform(put("/api/v1/user_account/{id}", userAccountDto.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userAccountDto)))
                .andExpect(status().isOk());

        // Validate the object in the database
        List<UserAccountDto> userList = userAccountService.list(null);
        assertThat(userList).hasSize(databaseSizeBeforeUpdate);
        UserAccountDto testDto = userList.get(userList.size() - 1);
        assertThat(testDto.getUsername()).isEqualTo(userAccountDto.getUsername());
        assertThat(testDto.getPassword()).isEqualTo(userAccountDto.getPassword());
        assertThat(testDto.getMobile()).isEqualTo(userAccountDto.getMobile());
        assertThat(testDto.getEmail()).isEqualTo(userAccountDto.getEmail());
        assertThat(testDto.getActivated()).isEqualTo(userAccountDto.getActivated());

    }

    @Test
    @Transactional
    public void testModifyExistingUsername() throws Exception {
        // Initialize the database with 2 objects
        UserAccountDto oneDto = create();
        userAccountService.create(oneDto);

        UserAccountDto anotherDto = create();
        userAccountService.create(anotherDto);

        // Update the object
        UserAccountDto updatedDto = userAccountService.get(oneDto.getId()).get();

        UserAccountDto userAccountDto = new UserAccountDto();
        userAccountDto.setId(updatedDto.getId());
        userAccountDto.setUsername(anotherDto.getUsername());// this username should already be used by anotherDto
        userAccountDto.setPassword(updatedDto.getPassword());
        userAccountDto.setMobile(updatedDto.getMobile());
        userAccountDto.setEmail(updatedDto.getEmail());
        userAccountDto.setActivated(updatedDto.getActivated());
        userAccountDto.setCreatedBy(updatedDto.getCreatedBy());
        userAccountDto.setLastModifiedBy(updatedDto.getLastModifiedBy());

        restUserAccountMockMvc.perform(put("/api/v1/user_account/{id}", userAccountDto.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userAccountDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void testModifyExistingMobile() throws Exception {
        // Initialize the database with 2 objects
        UserAccountDto oneDto = create();
        userAccountService.create(oneDto);

        UserAccountDto anotherDto = create();
        userAccountService.create(anotherDto);

        // Update the object
        UserAccountDto updatedDto = userAccountService.get(oneDto.getId()).get();

        UserAccountDto userAccountDto = new UserAccountDto();
        userAccountDto.setId(updatedDto.getId());
        userAccountDto.setUsername(updatedDto.getUsername());
        userAccountDto.setPassword(updatedDto.getPassword());
        userAccountDto.setMobile(anotherDto.getMobile());// this mobile should already be used by another
        userAccountDto.setEmail(updatedDto.getEmail());
        userAccountDto.setActivated(updatedDto.getActivated());
        userAccountDto.setCreatedBy(updatedDto.getCreatedBy());
        userAccountDto.setLastModifiedBy(updatedDto.getLastModifiedBy());

        restUserAccountMockMvc.perform(put("/api/v1/user_account/{id}", userAccountDto.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userAccountDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void testModifyExistingEmail() throws Exception {
        // Initialize the database with 2 objects
        UserAccountDto oneDto = create();
        userAccountService.create(oneDto);

        UserAccountDto anotherDto = create();
        userAccountService.create(anotherDto);

        // Update the object
        UserAccountDto updatedDto = userAccountService.get(oneDto.getId()).get();

        UserAccountDto userAccountDto = new UserAccountDto();
        userAccountDto.setId(updatedDto.getId());
        userAccountDto.setUsername(updatedDto.getUsername());
        userAccountDto.setPassword(updatedDto.getPassword());
        userAccountDto.setMobile(updatedDto.getMobile());
        userAccountDto.setEmail(anotherDto.getEmail());// this email should already be used by another
        userAccountDto.setActivated(updatedDto.getActivated());
        userAccountDto.setCreatedBy(updatedDto.getCreatedBy());
        userAccountDto.setLastModifiedBy(updatedDto.getLastModifiedBy());

        restUserAccountMockMvc.perform(put("/api/v1/user_account/{id}", userAccountDto.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userAccountDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void testDelete() throws Exception {
        // Initialize the database
        UserAccountDto oneDto = create();
        userAccountService.create(oneDto);
        int databaseSizeBeforeDelete = userAccountService.list(null).size();

        // Delete the object
        restUserAccountMockMvc.perform(delete("/api/v1/user_account/{id}", oneDto.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UserAccountDto> userList = userAccountService.list(null);
        assertThat(userList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void testEquals() throws Exception {
        TestUtil.equalsVerifier(UserAccountDto.class);
        UserAccountDto user1 = new UserAccountDto();
        user1.setUsername(DEFAULT_USERNAME + RandomStringUtils.randomAlphabetic(5));
        UserAccountDto user2 = new UserAccountDto();
        user2.setUsername(user1.getUsername());
        assertThat(user1).isEqualTo(user2);
        user2.setUsername(DEFAULT_USERNAME + RandomStringUtils.randomAlphabetic(5));
        assertThat(user1).isNotEqualTo(user2);
        user1.setUsername(null);
        assertThat(user1).isNotEqualTo(user2);
    }

    @Test
    public void testDto2Entity() {
        UserAccountDto dto = new UserAccountDto();
        dto.setId(Long.parseLong(RandomStringUtils.randomNumeric(8)));
        dto.setUsername(RandomStringUtils.randomAlphabetic(5));
        dto.setPassword(RandomStringUtils.randomAlphabetic(5));
        dto.setMobile(RandomStringUtils.randomAlphabetic(5));
        dto.setEmail(RandomStringUtils.randomAlphabetic(5));
        dto.setActivated(DictConstant.BYTE_YES_NO_N);
        dto.setCreatedBy(Long.parseLong(RandomStringUtils.randomNumeric(8)));
        dto.setCreatedDate(LocalDateTime.now());
        dto.setLastModifiedBy(Long.parseLong(RandomStringUtils.randomNumeric(8)));
        dto.setLastModifiedDate(LocalDateTime.now());
        dto.setMemo(RandomStringUtils.randomAlphabetic(5));
        dto.setDeleted(DictConstant.BYTE_YES_NO_N);

        UserAccount entity = userAccountMapStruct.fromDto2Entity(dto);

        assertThat(entity.getId()).isEqualTo(dto.getId());
        assertThat(entity.getUsername()).isEqualTo(dto.getUsername());
        assertThat(entity.getPassword()).isEqualTo(dto.getPassword());
        assertThat(entity.getMobile()).isEqualTo(dto.getMobile());
        assertThat(entity.getEmail()).isEqualTo(dto.getEmail());
        assertThat(entity.getActivated()).isEqualTo(dto.getActivated());
        assertThat(entity.getCreatedBy()).isEqualTo(dto.getCreatedBy());
        assertThat(entity.getCreatedDate()).isEqualTo(dto.getCreatedDate());
        assertThat(entity.getLastModifiedBy()).isEqualTo(dto.getLastModifiedBy());
        assertThat(entity.getLastModifiedDate()).isEqualTo(dto.getLastModifiedDate());
        assertThat(entity.getMemo()).isEqualTo(dto.getMemo());
        assertThat(entity.getDeleted()).isEqualTo(dto.getDeleted());
        assertThat(entity.toString()).isNotNull();
    }

    @Test
    public void testEntityToDto() {
        UserAccount entity = new UserAccount();
        entity.setId(Long.parseLong(RandomStringUtils.randomNumeric(8)));
        entity.setUsername(RandomStringUtils.randomAlphabetic(5));
        entity.setPassword(RandomStringUtils.randomAlphabetic(5));
        entity.setMobile(RandomStringUtils.randomAlphabetic(5));
        entity.setEmail(RandomStringUtils.randomAlphabetic(5));
        entity.setActivated(DictConstant.BYTE_YES_NO_N);
        entity.setCreatedBy(Long.parseLong(RandomStringUtils.randomNumeric(8)));
        entity.setCreatedDate(LocalDateTime.now());
        entity.setLastModifiedBy(Long.parseLong(RandomStringUtils.randomNumeric(8)));
        entity.setLastModifiedDate(LocalDateTime.now());
        entity.setMemo(RandomStringUtils.randomAlphabetic(5));
        entity.setDeleted(DictConstant.BYTE_YES_NO_N);

        UserAccountDto dto = userAccountMapStruct.fromEntity2Dto(entity);

        assertThat(dto.getId()).isEqualTo(entity.getId());
        assertThat(dto.getUsername()).isEqualTo(entity.getUsername());
        assertThat(dto.getPassword()).isEqualTo(entity.getPassword());
        assertThat(dto.getMobile()).isEqualTo(entity.getMobile());
        assertThat(dto.getEmail()).isEqualTo(entity.getEmail());
        assertThat(dto.getActivated()).isEqualTo(entity.getActivated());
        assertThat(dto.getCreatedBy()).isEqualTo(entity.getCreatedBy());
        assertThat(dto.getCreatedDate()).isEqualTo(entity.getCreatedDate());
        assertThat(dto.getLastModifiedBy()).isEqualTo(entity.getLastModifiedBy());
        assertThat(dto.getLastModifiedDate()).isEqualTo(entity.getLastModifiedDate());
        assertThat(dto.getMemo()).isEqualTo(entity.getMemo());
        assertThat(dto.getDeleted()).isEqualTo(entity.getDeleted());
        assertThat(dto.toString()).isNotNull();
    }

}

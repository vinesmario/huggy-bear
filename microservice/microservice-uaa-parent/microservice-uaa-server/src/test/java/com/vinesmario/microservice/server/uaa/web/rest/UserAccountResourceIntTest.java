package com.vinesmario.microservice.server.uaa.web.rest;

import com.vinesmario.microservice.client.uaa.dto.UserAccountDTO;
import com.vinesmario.common.constant.DictConstant;
import com.vinesmario.microservice.server.common.web.rest.TestUtil;
import com.vinesmario.microservice.server.common.web.rest.errors.ExceptionTranslator;
import com.vinesmario.microservice.server.uaa.UaaServerApplicationIntTest;
import com.vinesmario.microservice.server.uaa.entity.UserAccount;
import com.vinesmario.microservice.server.uaa.mapstruct.UserAccountMapStructImpl;
import com.vinesmario.microservice.server.uaa.service.UserAccountService;
import com.vinesmario.microservice.server.uaa.web.rest.v1.UserAccountResource;
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
@SpringBootTest(classes = UaaServerApplicationIntTest.class)
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
    public static UserAccountDTO create() {
        UserAccountDTO userAccountDTO = new UserAccountDTO();
        userAccountDTO.setUsername(RandomStringUtils.randomAlphabetic(5));
        userAccountDTO.setPassword(RandomStringUtils.randomAlphabetic(5));
        userAccountDTO.setMobile(RandomStringUtils.randomAlphabetic(5));
        userAccountDTO.setEmail(RandomStringUtils.randomAlphabetic(5));
        userAccountDTO.setActivated(DictConstant.BYTE_YES_NO_N);
        return userAccountDTO;
    }

    @Test
    @Transactional
    public void testCreate() throws Exception {
        int databaseSizeBeforeCreate = userAccountService.list(null).size();

        // Create the object
        UserAccountDTO oneDTO = create();
        restUserAccountMockMvc.perform(post("/api/v1/user_account")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oneDTO)))
                .andExpect(status().isOk());

        // Validate the User in the database
        List<UserAccountDTO> userAccountDTOList = userAccountService.list(null);
        assertThat(userAccountDTOList).hasSize(databaseSizeBeforeCreate + 1);
        UserAccountDTO testDTO = userAccountDTOList.get(userAccountDTOList.size() - 1);
        assertThat(testDTO.getUsername()).isEqualTo(oneDTO.getUsername());
        assertThat(testDTO.getPassword()).isEqualTo(oneDTO.getPassword());
        assertThat(testDTO.getMobile()).isEqualTo(oneDTO.getMobile());
        assertThat(testDTO.getEmail()).isEqualTo(oneDTO.getEmail());
        assertThat(testDTO.getActivated()).isEqualTo(oneDTO.getActivated());
    }

    @Test
    @Transactional
    public void testCreateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userAccountService.list(null).size();

        UserAccountDTO oneDTO = create();
        oneDTO.setId(DEFAULT_ID);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserAccountMockMvc.perform(post("/api/v1/user_account")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oneDTO)))
                .andExpect(status().isBadRequest());

        // Validate the object in the database
        List<UserAccountDTO> userAccountDTOList = userAccountService.list(null);
        assertThat(userAccountDTOList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void testCreateWithExistingUsername() throws Exception {
        // Initialize the database
        UserAccountDTO oneDTO = create();
        userAccountService.create(oneDTO);
        int databaseSizeBeforeCreate = userAccountService.list(null).size();

        UserAccountDTO userAccountDTO = new UserAccountDTO();
        userAccountDTO.setUsername(oneDTO.getUsername());// this username should already be used
        userAccountDTO.setPassword(RandomStringUtils.random(64));
        userAccountDTO.setActivated(DictConstant.BYTE_YES_NO_N);

        // Create the object
        restUserAccountMockMvc.perform(post("/api/v1/user_account")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userAccountDTO)))
                .andExpect(status().isBadRequest());

        // Validate the object in the database
        List<UserAccountDTO> userList = userAccountService.list(null);
        assertThat(userList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void testCreateWithExistingMobile() throws Exception {
        // Initialize the database
        UserAccountDTO oneDTO = create();
        userAccountService.create(oneDTO);
        int databaseSizeBeforeCreate = userAccountService.list(null).size();

        UserAccountDTO userAccountDTO = new UserAccountDTO();
        userAccountDTO.setUsername(DEFAULT_USERNAME + RandomStringUtils.randomAlphabetic(5));
        userAccountDTO.setPassword(RandomStringUtils.random(64));
        userAccountDTO.setMobile(oneDTO.getMobile());// this mobile should already be used
        userAccountDTO.setActivated(DictConstant.BYTE_YES_NO_N);

        // Create the object
        restUserAccountMockMvc.perform(post("/api/v1/user_account")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userAccountDTO)))
                .andExpect(status().isBadRequest());

        // Validate the object in the database
        List<UserAccountDTO> userList = userAccountService.list(null);
        assertThat(userList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void testCreateWithExistingEmail() throws Exception {
        // Initialize the database
        UserAccountDTO oneDTO = create();
        userAccountService.create(oneDTO);
        int databaseSizeBeforeCreate = userAccountService.list(null).size();

        UserAccountDTO userAccountDTO = new UserAccountDTO();
        userAccountDTO.setUsername(DEFAULT_USERNAME + RandomStringUtils.randomAlphabetic(5));
        userAccountDTO.setPassword(RandomStringUtils.random(64));
        userAccountDTO.setEmail(oneDTO.getEmail());// this email should already be used
        userAccountDTO.setActivated(DictConstant.BYTE_YES_NO_N);

        // Create the object
        restUserAccountMockMvc.perform(post("/api/v1/user_account")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userAccountDTO)))
                .andExpect(status().isBadRequest());

        // Validate the object in the database
        List<UserAccountDTO> userAccountDTOList = userAccountService.list(null);
        assertThat(userAccountDTOList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void testSearch() throws Exception {
        // Initialize the database
        UserAccountDTO oneDTO = create();
        userAccountService.create(oneDTO);

        // Get all the objects
        restUserAccountMockMvc.perform(get("/api/v1/user_account")
                .param("pageNumber", "1")
                .param("pageSize", "1")
                .param("sort", "id,desc")
                .param("sort", "username,desc")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].username").value(hasItem(oneDTO.getUsername())))
                .andExpect(jsonPath("$.[*].password").value(hasItem(oneDTO.getPassword())))
                .andExpect(jsonPath("$.[*].mobile").value(hasItem(oneDTO.getMobile())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(oneDTO.getEmail())))
                .andExpect(jsonPath("$.[*].activated").value(hasItem(Integer.valueOf(oneDTO.getActivated()))))
        ;
    }

    @Test
    @Transactional
    public void testGet() throws Exception {
        // Initialize the database
        UserAccountDTO oneDTO = create();
        userAccountService.create(oneDTO);

        // Get the object
        restUserAccountMockMvc.perform(get("/api/v1/user_account/{id}", oneDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.username").value(oneDTO.getUsername()))
                .andExpect(jsonPath("$.password").value(oneDTO.getPassword()))
                .andExpect(jsonPath("$.mobile").value(oneDTO.getMobile()))
                .andExpect(jsonPath("$.email").value(oneDTO.getEmail()))
                .andExpect(jsonPath("$.activated").value(Integer.valueOf(oneDTO.getActivated())))
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
        UserAccountDTO oneDTO = create();
        userAccountService.create(oneDTO);
        int databaseSizeBeforeUpdate = userAccountService.list(null).size();

        // Update the object
        UserAccountDTO updatedDTO = userAccountService.get(oneDTO.getId()).get();

        UserAccountDTO userAccountDTO = new UserAccountDTO();
        userAccountDTO.setId(updatedDTO.getId());
        userAccountDTO.setUsername(RandomStringUtils.randomAlphabetic(5));
        userAccountDTO.setPassword(RandomStringUtils.randomAlphabetic(5));
        userAccountDTO.setMobile(RandomStringUtils.randomAlphabetic(5));
        userAccountDTO.setEmail(RandomStringUtils.randomAlphabetic(5));
        userAccountDTO.setActivated(DictConstant.BYTE_YES_NO_N);
        userAccountDTO.setCreatedBy(updatedDTO.getCreatedBy());
        userAccountDTO.setLastModifiedBy(updatedDTO.getLastModifiedBy());
        userAccountDTO.setDeleted(DictConstant.BYTE_YES_NO_N);

        restUserAccountMockMvc.perform(put("/api/v1/user_account/{id}", userAccountDTO.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userAccountDTO)))
                .andExpect(status().isOk());

        // Validate the object in the database
        List<UserAccountDTO> userList = userAccountService.list(null);
        assertThat(userList).hasSize(databaseSizeBeforeUpdate);
        UserAccountDTO testDTO = userList.get(userList.size() - 1);
        assertThat(testDTO.getUsername()).isEqualTo(userAccountDTO.getUsername());
        assertThat(testDTO.getPassword()).isEqualTo(userAccountDTO.getPassword());
        assertThat(testDTO.getMobile()).isEqualTo(userAccountDTO.getMobile());
        assertThat(testDTO.getEmail()).isEqualTo(userAccountDTO.getEmail());
        assertThat(testDTO.getActivated()).isEqualTo(userAccountDTO.getActivated());

    }

    @Test
    @Transactional
    public void testModifyExistingUsername() throws Exception {
        // Initialize the database with 2 objects
        UserAccountDTO oneDTO = create();
        userAccountService.create(oneDTO);

        UserAccountDTO anotherDTO = create();
        userAccountService.create(anotherDTO);

        // Update the object
        UserAccountDTO updatedDTO = userAccountService.get(oneDTO.getId()).get();

        UserAccountDTO userAccountDTO = new UserAccountDTO();
        userAccountDTO.setId(updatedDTO.getId());
        userAccountDTO.setUsername(anotherDTO.getUsername());// this username should already be used by anotherDTO
        userAccountDTO.setPassword(updatedDTO.getPassword());
        userAccountDTO.setMobile(updatedDTO.getMobile());
        userAccountDTO.setEmail(updatedDTO.getEmail());
        userAccountDTO.setActivated(updatedDTO.getActivated());
        userAccountDTO.setCreatedBy(updatedDTO.getCreatedBy());
        userAccountDTO.setLastModifiedBy(updatedDTO.getLastModifiedBy());

        restUserAccountMockMvc.perform(put("/api/v1/user_account/{id}", userAccountDTO.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userAccountDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void testModifyExistingMobile() throws Exception {
        // Initialize the database with 2 objects
        UserAccountDTO oneDTO = create();
        userAccountService.create(oneDTO);

        UserAccountDTO anotherDTO = create();
        userAccountService.create(anotherDTO);

        // Update the object
        UserAccountDTO updatedDTO = userAccountService.get(oneDTO.getId()).get();

        UserAccountDTO userAccountDTO = new UserAccountDTO();
        userAccountDTO.setId(updatedDTO.getId());
        userAccountDTO.setUsername(updatedDTO.getUsername());
        userAccountDTO.setPassword(updatedDTO.getPassword());
        userAccountDTO.setMobile(anotherDTO.getMobile());// this mobile should already be used by another
        userAccountDTO.setEmail(updatedDTO.getEmail());
        userAccountDTO.setActivated(updatedDTO.getActivated());
        userAccountDTO.setCreatedBy(updatedDTO.getCreatedBy());
        userAccountDTO.setLastModifiedBy(updatedDTO.getLastModifiedBy());

        restUserAccountMockMvc.perform(put("/api/v1/user_account/{id}", userAccountDTO.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userAccountDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void testModifyExistingEmail() throws Exception {
        // Initialize the database with 2 objects
        UserAccountDTO oneDTO = create();
        userAccountService.create(oneDTO);

        UserAccountDTO anotherDTO = create();
        userAccountService.create(anotherDTO);

        // Update the object
        UserAccountDTO updatedDTO = userAccountService.get(oneDTO.getId()).get();

        UserAccountDTO userAccountDTO = new UserAccountDTO();
        userAccountDTO.setId(updatedDTO.getId());
        userAccountDTO.setUsername(updatedDTO.getUsername());
        userAccountDTO.setPassword(updatedDTO.getPassword());
        userAccountDTO.setMobile(updatedDTO.getMobile());
        userAccountDTO.setEmail(anotherDTO.getEmail());// this email should already be used by another
        userAccountDTO.setActivated(updatedDTO.getActivated());
        userAccountDTO.setCreatedBy(updatedDTO.getCreatedBy());
        userAccountDTO.setLastModifiedBy(updatedDTO.getLastModifiedBy());

        restUserAccountMockMvc.perform(put("/api/v1/user_account/{id}", userAccountDTO.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userAccountDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void testDelete() throws Exception {
        // Initialize the database
        UserAccountDTO oneDTO = create();
        userAccountService.create(oneDTO);
        int databaseSizeBeforeDelete = userAccountService.list(null).size();

        // Delete the object
        restUserAccountMockMvc.perform(delete("/api/v1/user_account/{id}", oneDTO.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UserAccountDTO> userList = userAccountService.list(null);
        assertThat(userList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void testEquals() throws Exception {
        TestUtil.equalsVerifier(UserAccountDTO.class);
        UserAccountDTO user1 = new UserAccountDTO();
        user1.setUsername(DEFAULT_USERNAME + RandomStringUtils.randomAlphabetic(5));
        UserAccountDTO user2 = new UserAccountDTO();
        user2.setUsername(user1.getUsername());
        assertThat(user1).isEqualTo(user2);
        user2.setUsername(DEFAULT_USERNAME + RandomStringUtils.randomAlphabetic(5));
        assertThat(user1).isNotEqualTo(user2);
        user1.setUsername(null);
        assertThat(user1).isNotEqualTo(user2);
    }

    @Test
    public void testDTO2Entity() {
        UserAccountDTO dto = new UserAccountDTO();
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

        UserAccount entity = userAccountMapStruct.fromDTO2Entity(dto);

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
    public void testEntityToDTO() {
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

        UserAccountDTO dto = userAccountMapStruct.fromEntity2DTO(entity);

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

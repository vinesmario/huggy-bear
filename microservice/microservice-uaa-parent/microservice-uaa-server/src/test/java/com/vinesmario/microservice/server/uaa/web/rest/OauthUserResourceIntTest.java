package com.vinesmario.microservice.server.uaa.web.rest;

import com.vinesmario.microservice.client.uaa.dto.OauthUserDTO;
import com.vinesmario.common.constant.DictConstant;
import com.vinesmario.microservice.server.common.web.rest.TestUtil;
import com.vinesmario.microservice.server.common.web.rest.errors.ExceptionTranslator;
import com.vinesmario.microservice.server.uaa.UaaServerApplicationIntTest;
import com.vinesmario.microservice.server.uaa.entity.OauthUser;
import com.vinesmario.microservice.server.uaa.mapstruct.OauthUserMapStructImpl;
import com.vinesmario.microservice.server.uaa.service.OauthUserService;
import com.vinesmario.microservice.server.uaa.web.rest.v1.OauthUserResource;
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
 * Test class for the OauthUserResource REST controller.
 *
 * @see OauthUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UaaServerApplicationIntTest.class)
public class OauthUserResourceIntTest {

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

    private MockMvc restOauthUserMockMvc;

    @Autowired
    private OauthUserResource oauthUserResource;
    @Autowired
    private OauthUserService oauthUserService;
    @Autowired
    private OauthUserMapStructImpl oauthUserMapStruct;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.restOauthUserMockMvc = MockMvcBuilders.standaloneSetup(oauthUserResource)
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
    public static OauthUserDTO create() {
        OauthUserDTO oauthUserDTO = new OauthUserDTO();
        oauthUserDTO.setUsername(RandomStringUtils.randomAlphabetic(5));
        oauthUserDTO.setPassword(RandomStringUtils.randomAlphabetic(5));
        oauthUserDTO.setMobile(RandomStringUtils.randomAlphabetic(5));
        oauthUserDTO.setEmail(RandomStringUtils.randomAlphabetic(5));
        oauthUserDTO.setActivated(DictConstant.BYTE_YES_NO_N);
        return oauthUserDTO;
    }

    @Test
    @Transactional
    public void testCreate() throws Exception {
        int databaseSizeBeforeCreate = oauthUserService.list(null).size();

        // Create the object
        OauthUserDTO oneDTO = create();
        restOauthUserMockMvc.perform(post("/api/v1/oauth_user")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oneDTO)))
                .andExpect(status().isOk());

        // Validate the User in the database
        List<OauthUserDTO> oauthUserDTOList = oauthUserService.list(null);
        assertThat(oauthUserDTOList).hasSize(databaseSizeBeforeCreate + 1);
        OauthUserDTO testDTO = oauthUserDTOList.get(oauthUserDTOList.size() - 1);
        assertThat(testDTO.getUsername()).isEqualTo(oneDTO.getUsername());
        assertThat(testDTO.getPassword()).isEqualTo(oneDTO.getPassword());
        assertThat(testDTO.getMobile()).isEqualTo(oneDTO.getMobile());
        assertThat(testDTO.getEmail()).isEqualTo(oneDTO.getEmail());
        assertThat(testDTO.getActivated()).isEqualTo(oneDTO.getActivated());
    }

    @Test
    @Transactional
    public void testCreateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = oauthUserService.list(null).size();

        OauthUserDTO oneDTO = create();
        oneDTO.setId(DEFAULT_ID);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOauthUserMockMvc.perform(post("/api/v1/oauth_user")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oneDTO)))
                .andExpect(status().isBadRequest());

        // Validate the object in the database
        List<OauthUserDTO> oauthUserDTOList = oauthUserService.list(null);
        assertThat(oauthUserDTOList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void testCreateWithExistingUsername() throws Exception {
        // Initialize the database
        OauthUserDTO oneDTO = create();
        oauthUserService.create(oneDTO);
        int databaseSizeBeforeCreate = oauthUserService.list(null).size();

        OauthUserDTO oauthUserDTO = new OauthUserDTO();
        oauthUserDTO.setUsername(oneDTO.getUsername());// this username should already be used
        oauthUserDTO.setPassword(RandomStringUtils.random(64));
        oauthUserDTO.setActivated(DictConstant.BYTE_YES_NO_N);

        // Create the object
        restOauthUserMockMvc.perform(post("/api/v1/oauth_user")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oauthUserDTO)))
                .andExpect(status().isBadRequest());

        // Validate the object in the database
        List<OauthUserDTO> userList = oauthUserService.list(null);
        assertThat(userList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void testCreateWithExistingMobile() throws Exception {
        // Initialize the database
        OauthUserDTO oneDTO = create();
        oauthUserService.create(oneDTO);
        int databaseSizeBeforeCreate = oauthUserService.list(null).size();

        OauthUserDTO oauthUserDTO = new OauthUserDTO();
        oauthUserDTO.setUsername(DEFAULT_USERNAME + RandomStringUtils.randomAlphabetic(5));
        oauthUserDTO.setPassword(RandomStringUtils.random(64));
        oauthUserDTO.setMobile(oneDTO.getMobile());// this mobile should already be used
        oauthUserDTO.setActivated(DictConstant.BYTE_YES_NO_N);

        // Create the object
        restOauthUserMockMvc.perform(post("/api/v1/oauth_user")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oauthUserDTO)))
                .andExpect(status().isBadRequest());

        // Validate the object in the database
        List<OauthUserDTO> userList = oauthUserService.list(null);
        assertThat(userList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void testCreateWithExistingEmail() throws Exception {
        // Initialize the database
        OauthUserDTO oneDTO = create();
        oauthUserService.create(oneDTO);
        int databaseSizeBeforeCreate = oauthUserService.list(null).size();

        OauthUserDTO oauthUserDTO = new OauthUserDTO();
        oauthUserDTO.setUsername(DEFAULT_USERNAME + RandomStringUtils.randomAlphabetic(5));
        oauthUserDTO.setPassword(RandomStringUtils.random(64));
        oauthUserDTO.setEmail(oneDTO.getEmail());// this email should already be used
        oauthUserDTO.setActivated(DictConstant.BYTE_YES_NO_N);

        // Create the object
        restOauthUserMockMvc.perform(post("/api/v1/oauth_user")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oauthUserDTO)))
                .andExpect(status().isBadRequest());

        // Validate the object in the database
        List<OauthUserDTO> oauthUserDTOList = oauthUserService.list(null);
        assertThat(oauthUserDTOList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void testSearch() throws Exception {
        // Initialize the database
        OauthUserDTO oneDTO = create();
        oauthUserService.create(oneDTO);

        // Get all the objects
        restOauthUserMockMvc.perform(get("/api/v1/oauth_user")
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
        OauthUserDTO oneDTO = create();
        oauthUserService.create(oneDTO);

        // Get the object
        restOauthUserMockMvc.perform(get("/api/v1/oauth_user/{id}", oneDTO.getId()))
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
        restOauthUserMockMvc.perform(get("/api/v1/oauth_user/{id}", -1))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void testModify() throws Exception {
        // Initialize the database
        OauthUserDTO oneDTO = create();
        oauthUserService.create(oneDTO);
        int databaseSizeBeforeUpdate = oauthUserService.list(null).size();

        // Update the object
        OauthUserDTO updatedDTO = oauthUserService.get(oneDTO.getId()).get();

        OauthUserDTO oauthUserDTO = new OauthUserDTO();
        oauthUserDTO.setId(updatedDTO.getId());
        oauthUserDTO.setUsername(RandomStringUtils.randomAlphabetic(5));
        oauthUserDTO.setPassword(RandomStringUtils.randomAlphabetic(5));
        oauthUserDTO.setMobile(RandomStringUtils.randomAlphabetic(5));
        oauthUserDTO.setEmail(RandomStringUtils.randomAlphabetic(5));
        oauthUserDTO.setActivated(DictConstant.BYTE_YES_NO_N);
        oauthUserDTO.setCreatedBy(updatedDTO.getCreatedBy());
        oauthUserDTO.setLastModifiedBy(updatedDTO.getLastModifiedBy());
        oauthUserDTO.setDeleted(DictConstant.BYTE_YES_NO_N);

        restOauthUserMockMvc.perform(put("/api/v1/oauth_user/{id}", oauthUserDTO.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oauthUserDTO)))
                .andExpect(status().isOk());

        // Validate the object in the database
        List<OauthUserDTO> userList = oauthUserService.list(null);
        assertThat(userList).hasSize(databaseSizeBeforeUpdate);
        OauthUserDTO testDTO = userList.get(userList.size() - 1);
        assertThat(testDTO.getUsername()).isEqualTo(oauthUserDTO.getUsername());
        assertThat(testDTO.getPassword()).isEqualTo(oauthUserDTO.getPassword());
        assertThat(testDTO.getMobile()).isEqualTo(oauthUserDTO.getMobile());
        assertThat(testDTO.getEmail()).isEqualTo(oauthUserDTO.getEmail());
        assertThat(testDTO.getActivated()).isEqualTo(oauthUserDTO.getActivated());

    }

    @Test
    @Transactional
    public void testModifyExistingUsername() throws Exception {
        // Initialize the database with 2 objects
        OauthUserDTO oneDTO = create();
        oauthUserService.create(oneDTO);

        OauthUserDTO anotherDTO = create();
        oauthUserService.create(anotherDTO);

        // Update the object
        OauthUserDTO updatedDTO = oauthUserService.get(oneDTO.getId()).get();

        OauthUserDTO oauthUserDTO = new OauthUserDTO();
        oauthUserDTO.setId(updatedDTO.getId());
        oauthUserDTO.setUsername(anotherDTO.getUsername());// this username should already be used by anotherDTO
        oauthUserDTO.setPassword(updatedDTO.getPassword());
        oauthUserDTO.setMobile(updatedDTO.getMobile());
        oauthUserDTO.setEmail(updatedDTO.getEmail());
        oauthUserDTO.setActivated(updatedDTO.getActivated());
        oauthUserDTO.setCreatedBy(updatedDTO.getCreatedBy());
        oauthUserDTO.setLastModifiedBy(updatedDTO.getLastModifiedBy());

        restOauthUserMockMvc.perform(put("/api/v1/oauth_user/{id}", oauthUserDTO.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oauthUserDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void testModifyExistingMobile() throws Exception {
        // Initialize the database with 2 objects
        OauthUserDTO oneDTO = create();
        oauthUserService.create(oneDTO);

        OauthUserDTO anotherDTO = create();
        oauthUserService.create(anotherDTO);

        // Update the object
        OauthUserDTO updatedDTO = oauthUserService.get(oneDTO.getId()).get();

        OauthUserDTO oauthUserDTO = new OauthUserDTO();
        oauthUserDTO.setId(updatedDTO.getId());
        oauthUserDTO.setUsername(updatedDTO.getUsername());
        oauthUserDTO.setPassword(updatedDTO.getPassword());
        oauthUserDTO.setMobile(anotherDTO.getMobile());// this mobile should already be used by another
        oauthUserDTO.setEmail(updatedDTO.getEmail());
        oauthUserDTO.setActivated(updatedDTO.getActivated());
        oauthUserDTO.setCreatedBy(updatedDTO.getCreatedBy());
        oauthUserDTO.setLastModifiedBy(updatedDTO.getLastModifiedBy());

        restOauthUserMockMvc.perform(put("/api/v1/oauth_user/{id}", oauthUserDTO.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oauthUserDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void testModifyExistingEmail() throws Exception {
        // Initialize the database with 2 objects
        OauthUserDTO oneDTO = create();
        oauthUserService.create(oneDTO);

        OauthUserDTO anotherDTO = create();
        oauthUserService.create(anotherDTO);

        // Update the object
        OauthUserDTO updatedDTO = oauthUserService.get(oneDTO.getId()).get();

        OauthUserDTO oauthUserDTO = new OauthUserDTO();
        oauthUserDTO.setId(updatedDTO.getId());
        oauthUserDTO.setUsername(updatedDTO.getUsername());
        oauthUserDTO.setPassword(updatedDTO.getPassword());
        oauthUserDTO.setMobile(updatedDTO.getMobile());
        oauthUserDTO.setEmail(anotherDTO.getEmail());// this email should already be used by another
        oauthUserDTO.setActivated(updatedDTO.getActivated());
        oauthUserDTO.setCreatedBy(updatedDTO.getCreatedBy());
        oauthUserDTO.setLastModifiedBy(updatedDTO.getLastModifiedBy());

        restOauthUserMockMvc.perform(put("/api/v1/oauth_user/{id}", oauthUserDTO.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oauthUserDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void testDelete() throws Exception {
        // Initialize the database
        OauthUserDTO oneDTO = create();
        oauthUserService.create(oneDTO);
        int databaseSizeBeforeDelete = oauthUserService.list(null).size();

        // Delete the object
        restOauthUserMockMvc.perform(delete("/api/v1/oauth_user/{id}", oneDTO.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<OauthUserDTO> userList = oauthUserService.list(null);
        assertThat(userList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void testEquals() throws Exception {
        TestUtil.equalsVerifier(OauthUserDTO.class);
        OauthUserDTO user1 = new OauthUserDTO();
        user1.setUsername(DEFAULT_USERNAME + RandomStringUtils.randomAlphabetic(5));
        OauthUserDTO user2 = new OauthUserDTO();
        user2.setUsername(user1.getUsername());
        assertThat(user1).isEqualTo(user2);
        user2.setUsername(DEFAULT_USERNAME + RandomStringUtils.randomAlphabetic(5));
        assertThat(user1).isNotEqualTo(user2);
        user1.setUsername(null);
        assertThat(user1).isNotEqualTo(user2);
    }

    @Test
    public void testDTO2Entity() {
        OauthUserDTO dto = new OauthUserDTO();
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

        OauthUser entity = oauthUserMapStruct.fromDTO2Entity(dto);

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
        OauthUser entity = new OauthUser();
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

        OauthUserDTO dto = oauthUserMapStruct.fromEntity2DTO(entity);

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

package br.com.agroplanner.web.rest;

import br.com.agroplanner.AgroplannerApp;
import br.com.agroplanner.domain.PropriedadeFoto;
import br.com.agroplanner.domain.Propriedade;
import br.com.agroplanner.repository.PropriedadeFotoRepository;
import br.com.agroplanner.service.PropriedadeFotoService;
import br.com.agroplanner.service.dto.PropriedadeFotoDTO;
import br.com.agroplanner.service.mapper.PropriedadeFotoMapper;
import br.com.agroplanner.web.rest.errors.ExceptionTranslator;
import br.com.agroplanner.service.dto.PropriedadeFotoCriteria;
import br.com.agroplanner.service.PropriedadeFotoQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static br.com.agroplanner.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link PropriedadeFotoResource} REST controller.
 */
@SpringBootTest(classes = AgroplannerApp.class)
public class PropriedadeFotoResourceIT {

    private static final byte[] DEFAULT_FOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_CONTENT_TYPE = "image/png";

    @Autowired
    private PropriedadeFotoRepository propriedadeFotoRepository;

    @Autowired
    private PropriedadeFotoMapper propriedadeFotoMapper;

    @Autowired
    private PropriedadeFotoService propriedadeFotoService;

    @Autowired
    private PropriedadeFotoQueryService propriedadeFotoQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPropriedadeFotoMockMvc;

    private PropriedadeFoto propriedadeFoto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PropriedadeFotoResource propriedadeFotoResource = new PropriedadeFotoResource(propriedadeFotoService, propriedadeFotoQueryService);
        this.restPropriedadeFotoMockMvc = MockMvcBuilders.standaloneSetup(propriedadeFotoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PropriedadeFoto createEntity(EntityManager em) {
        PropriedadeFoto propriedadeFoto = new PropriedadeFoto()
            .foto(DEFAULT_FOTO)
            .fotoContentType(DEFAULT_FOTO_CONTENT_TYPE);
        // Add required entity
        Propriedade propriedade;
        if (TestUtil.findAll(em, Propriedade.class).isEmpty()) {
            propriedade = PropriedadeResourceIT.createEntity(em);
            em.persist(propriedade);
            em.flush();
        } else {
            propriedade = TestUtil.findAll(em, Propriedade.class).get(0);
        }
        propriedadeFoto.setPropriedade(propriedade);
        return propriedadeFoto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PropriedadeFoto createUpdatedEntity(EntityManager em) {
        PropriedadeFoto propriedadeFoto = new PropriedadeFoto()
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE);
        // Add required entity
        Propriedade propriedade;
        if (TestUtil.findAll(em, Propriedade.class).isEmpty()) {
            propriedade = PropriedadeResourceIT.createUpdatedEntity(em);
            em.persist(propriedade);
            em.flush();
        } else {
            propriedade = TestUtil.findAll(em, Propriedade.class).get(0);
        }
        propriedadeFoto.setPropriedade(propriedade);
        return propriedadeFoto;
    }

    @BeforeEach
    public void initTest() {
        propriedadeFoto = createEntity(em);
    }

    @Test
    @Transactional
    public void createPropriedadeFoto() throws Exception {
        int databaseSizeBeforeCreate = propriedadeFotoRepository.findAll().size();

        // Create the PropriedadeFoto
        PropriedadeFotoDTO propriedadeFotoDTO = propriedadeFotoMapper.toDto(propriedadeFoto);
        restPropriedadeFotoMockMvc.perform(post("/api/propriedade-fotos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propriedadeFotoDTO)))
            .andExpect(status().isCreated());

        // Validate the PropriedadeFoto in the database
        List<PropriedadeFoto> propriedadeFotoList = propriedadeFotoRepository.findAll();
        assertThat(propriedadeFotoList).hasSize(databaseSizeBeforeCreate + 1);
        PropriedadeFoto testPropriedadeFoto = propriedadeFotoList.get(propriedadeFotoList.size() - 1);
        assertThat(testPropriedadeFoto.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testPropriedadeFoto.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createPropriedadeFotoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = propriedadeFotoRepository.findAll().size();

        // Create the PropriedadeFoto with an existing ID
        propriedadeFoto.setId(1L);
        PropriedadeFotoDTO propriedadeFotoDTO = propriedadeFotoMapper.toDto(propriedadeFoto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPropriedadeFotoMockMvc.perform(post("/api/propriedade-fotos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propriedadeFotoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PropriedadeFoto in the database
        List<PropriedadeFoto> propriedadeFotoList = propriedadeFotoRepository.findAll();
        assertThat(propriedadeFotoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPropriedadeFotos() throws Exception {
        // Initialize the database
        propriedadeFotoRepository.saveAndFlush(propriedadeFoto);

        // Get all the propriedadeFotoList
        restPropriedadeFotoMockMvc.perform(get("/api/propriedade-fotos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(propriedadeFoto.getId().intValue())))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))));
    }
    
    @Test
    @Transactional
    public void getPropriedadeFoto() throws Exception {
        // Initialize the database
        propriedadeFotoRepository.saveAndFlush(propriedadeFoto);

        // Get the propriedadeFoto
        restPropriedadeFotoMockMvc.perform(get("/api/propriedade-fotos/{id}", propriedadeFoto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(propriedadeFoto.getId().intValue()))
            .andExpect(jsonPath("$.fotoContentType").value(DEFAULT_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto").value(Base64Utils.encodeToString(DEFAULT_FOTO)));
    }

    @Test
    @Transactional
    public void getAllPropriedadeFotosByPropriedadeIsEqualToSomething() throws Exception {
        // Get already existing entity
        Propriedade propriedade = propriedadeFoto.getPropriedade();
        propriedadeFotoRepository.saveAndFlush(propriedadeFoto);
        Long propriedadeId = propriedade.getId();

        // Get all the propriedadeFotoList where propriedade equals to propriedadeId
        defaultPropriedadeFotoShouldBeFound("propriedadeId.equals=" + propriedadeId);

        // Get all the propriedadeFotoList where propriedade equals to propriedadeId + 1
        defaultPropriedadeFotoShouldNotBeFound("propriedadeId.equals=" + (propriedadeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPropriedadeFotoShouldBeFound(String filter) throws Exception {
        restPropriedadeFotoMockMvc.perform(get("/api/propriedade-fotos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(propriedadeFoto.getId().intValue())))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))));

        // Check, that the count call also returns 1
        restPropriedadeFotoMockMvc.perform(get("/api/propriedade-fotos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPropriedadeFotoShouldNotBeFound(String filter) throws Exception {
        restPropriedadeFotoMockMvc.perform(get("/api/propriedade-fotos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPropriedadeFotoMockMvc.perform(get("/api/propriedade-fotos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPropriedadeFoto() throws Exception {
        // Get the propriedadeFoto
        restPropriedadeFotoMockMvc.perform(get("/api/propriedade-fotos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePropriedadeFoto() throws Exception {
        // Initialize the database
        propriedadeFotoRepository.saveAndFlush(propriedadeFoto);

        int databaseSizeBeforeUpdate = propriedadeFotoRepository.findAll().size();

        // Update the propriedadeFoto
        PropriedadeFoto updatedPropriedadeFoto = propriedadeFotoRepository.findById(propriedadeFoto.getId()).get();
        // Disconnect from session so that the updates on updatedPropriedadeFoto are not directly saved in db
        em.detach(updatedPropriedadeFoto);
        updatedPropriedadeFoto
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE);
        PropriedadeFotoDTO propriedadeFotoDTO = propriedadeFotoMapper.toDto(updatedPropriedadeFoto);

        restPropriedadeFotoMockMvc.perform(put("/api/propriedade-fotos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propriedadeFotoDTO)))
            .andExpect(status().isOk());

        // Validate the PropriedadeFoto in the database
        List<PropriedadeFoto> propriedadeFotoList = propriedadeFotoRepository.findAll();
        assertThat(propriedadeFotoList).hasSize(databaseSizeBeforeUpdate);
        PropriedadeFoto testPropriedadeFoto = propriedadeFotoList.get(propriedadeFotoList.size() - 1);
        assertThat(testPropriedadeFoto.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testPropriedadeFoto.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPropriedadeFoto() throws Exception {
        int databaseSizeBeforeUpdate = propriedadeFotoRepository.findAll().size();

        // Create the PropriedadeFoto
        PropriedadeFotoDTO propriedadeFotoDTO = propriedadeFotoMapper.toDto(propriedadeFoto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPropriedadeFotoMockMvc.perform(put("/api/propriedade-fotos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propriedadeFotoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PropriedadeFoto in the database
        List<PropriedadeFoto> propriedadeFotoList = propriedadeFotoRepository.findAll();
        assertThat(propriedadeFotoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePropriedadeFoto() throws Exception {
        // Initialize the database
        propriedadeFotoRepository.saveAndFlush(propriedadeFoto);

        int databaseSizeBeforeDelete = propriedadeFotoRepository.findAll().size();

        // Delete the propriedadeFoto
        restPropriedadeFotoMockMvc.perform(delete("/api/propriedade-fotos/{id}", propriedadeFoto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<PropriedadeFoto> propriedadeFotoList = propriedadeFotoRepository.findAll();
        assertThat(propriedadeFotoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PropriedadeFoto.class);
        PropriedadeFoto propriedadeFoto1 = new PropriedadeFoto();
        propriedadeFoto1.setId(1L);
        PropriedadeFoto propriedadeFoto2 = new PropriedadeFoto();
        propriedadeFoto2.setId(propriedadeFoto1.getId());
        assertThat(propriedadeFoto1).isEqualTo(propriedadeFoto2);
        propriedadeFoto2.setId(2L);
        assertThat(propriedadeFoto1).isNotEqualTo(propriedadeFoto2);
        propriedadeFoto1.setId(null);
        assertThat(propriedadeFoto1).isNotEqualTo(propriedadeFoto2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PropriedadeFotoDTO.class);
        PropriedadeFotoDTO propriedadeFotoDTO1 = new PropriedadeFotoDTO();
        propriedadeFotoDTO1.setId(1L);
        PropriedadeFotoDTO propriedadeFotoDTO2 = new PropriedadeFotoDTO();
        assertThat(propriedadeFotoDTO1).isNotEqualTo(propriedadeFotoDTO2);
        propriedadeFotoDTO2.setId(propriedadeFotoDTO1.getId());
        assertThat(propriedadeFotoDTO1).isEqualTo(propriedadeFotoDTO2);
        propriedadeFotoDTO2.setId(2L);
        assertThat(propriedadeFotoDTO1).isNotEqualTo(propriedadeFotoDTO2);
        propriedadeFotoDTO1.setId(null);
        assertThat(propriedadeFotoDTO1).isNotEqualTo(propriedadeFotoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(propriedadeFotoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(propriedadeFotoMapper.fromId(null)).isNull();
    }
}

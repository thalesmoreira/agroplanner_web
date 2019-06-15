package br.com.agroplanner.web.rest;

import br.com.agroplanner.AgroplannerApp;
import br.com.agroplanner.domain.PropriedadeCaracteristica;
import br.com.agroplanner.repository.PropriedadeCaracteristicaRepository;
import br.com.agroplanner.domain.Propriedade;
import br.com.agroplanner.domain.Caracteristica;
import br.com.agroplanner.service.PropriedadeCaracteristicaService;
import br.com.agroplanner.service.dto.PropriedadeCaracteristicaDTO;
import br.com.agroplanner.service.mapper.PropriedadeCaracteristicaMapper;
import br.com.agroplanner.web.rest.errors.ExceptionTranslator;
import br.com.agroplanner.service.dto.PropriedadeCaracteristicaCriteria;
import br.com.agroplanner.service.PropriedadeCaracteristicaQueryService;

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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static br.com.agroplanner.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link Propriedade_caracteristicaResource} REST controller.
 */
@SpringBootTest(classes = AgroplannerApp.class)
public class PropriedadeCaracteristicaResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private PropriedadeCaracteristicaRepository propriedadeCaracteristicaRepository;

    @Autowired
    private PropriedadeCaracteristicaMapper propriedadeCaracteristicaMapper;

    @Autowired
    private PropriedadeCaracteristicaService propriedadeCaracteristicaService;

    @Autowired
    private PropriedadeCaracteristicaQueryService propriedadeCaracteristicaQueryService;

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

    private MockMvc restPropriedadeCaracteristicaMockMvc;

    private PropriedadeCaracteristica propriedadeCaracteristica;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PropriedadeCaracteristicaResource propriedadeCaracteristicaResource = new PropriedadeCaracteristicaResource(propriedadeCaracteristicaService, propriedadeCaracteristicaQueryService);
        this.restPropriedadeCaracteristicaMockMvc = MockMvcBuilders.standaloneSetup(propriedadeCaracteristicaResource)
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
    public static PropriedadeCaracteristica createEntity(EntityManager em) {
        PropriedadeCaracteristica propriedadeCaracteristica = new PropriedadeCaracteristica()
            .value(DEFAULT_VALUE);
        // Add required entity
        Propriedade propriedade;
        if (TestUtil.findAll(em, Propriedade.class).isEmpty()) {
            propriedade = PropriedadeResourceIT.createEntity(em);
            em.persist(propriedade);
            em.flush();
        } else {
            propriedade = TestUtil.findAll(em, Propriedade.class).get(0);
        }
        propriedadeCaracteristica.setPropriedade(propriedade);
        // Add required entity
        Caracteristica caracteristica;
        if (TestUtil.findAll(em, Caracteristica.class).isEmpty()) {
            caracteristica = CaracteristicaResourceIT.createEntity(em);
            em.persist(caracteristica);
            em.flush();
        } else {
            caracteristica = TestUtil.findAll(em, Caracteristica.class).get(0);
        }
        propriedadeCaracteristica.setCaracteristica(caracteristica);
        return propriedadeCaracteristica;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PropriedadeCaracteristica createUpdatedEntity(EntityManager em) {
        PropriedadeCaracteristica propriedadeCaracteristica = new PropriedadeCaracteristica()
            .value(UPDATED_VALUE);
        // Add required entity
        Propriedade propriedade;
        if (TestUtil.findAll(em, Propriedade.class).isEmpty()) {
            propriedade = PropriedadeResourceIT.createUpdatedEntity(em);
            em.persist(propriedade);
            em.flush();
        } else {
            propriedade = TestUtil.findAll(em, Propriedade.class).get(0);
        }
        propriedadeCaracteristica.setPropriedade(propriedade);
        // Add required entity
        Caracteristica caracteristica;
        if (TestUtil.findAll(em, Caracteristica.class).isEmpty()) {
            caracteristica = CaracteristicaResourceIT.createUpdatedEntity(em);
            em.persist(caracteristica);
            em.flush();
        } else {
            caracteristica = TestUtil.findAll(em, Caracteristica.class).get(0);
        }
        propriedadeCaracteristica.setCaracteristica(caracteristica);
        return propriedadeCaracteristica;
    }

    @BeforeEach
    public void initTest() {
        propriedadeCaracteristica = createEntity(em);
    }

    @Test
    @Transactional
    public void createPropriedade_caracteristica() throws Exception {
        int databaseSizeBeforeCreate = propriedadeCaracteristicaRepository.findAll().size();

        // Create the PropriedadeCaracteristica
        PropriedadeCaracteristicaDTO propriedadeCaracteristicaDTO = propriedadeCaracteristicaMapper.toDto(propriedadeCaracteristica);
        restPropriedadeCaracteristicaMockMvc.perform(post("/api/propriedade-caracteristicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propriedadeCaracteristicaDTO)))
            .andExpect(status().isCreated());

        // Validate the PropriedadeCaracteristica in the database
        List<PropriedadeCaracteristica> propriedadeCaracteristicaList = propriedadeCaracteristicaRepository.findAll();
        assertThat(propriedadeCaracteristicaList).hasSize(databaseSizeBeforeCreate + 1);
        PropriedadeCaracteristica testPropriedade_caracteristica = propriedadeCaracteristicaList.get(propriedadeCaracteristicaList.size() - 1);
        assertThat(testPropriedade_caracteristica.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createPropriedade_caracteristicaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = propriedadeCaracteristicaRepository.findAll().size();

        // Create the PropriedadeCaracteristica with an existing ID
        propriedadeCaracteristica.setId(1L);
        PropriedadeCaracteristicaDTO propriedadeCaracteristicaDTO = propriedadeCaracteristicaMapper.toDto(propriedadeCaracteristica);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPropriedadeCaracteristicaMockMvc.perform(post("/api/propriedade-caracteristicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propriedadeCaracteristicaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PropriedadeCaracteristica in the database
        List<PropriedadeCaracteristica> propriedadeCaracteristicaList = propriedadeCaracteristicaRepository.findAll();
        assertThat(propriedadeCaracteristicaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPropriedadeCaracteristicas() throws Exception {
        // Initialize the database
        propriedadeCaracteristicaRepository.saveAndFlush(propriedadeCaracteristica);

        // Get all the propriedadeCaracteristicaList
        restPropriedadeCaracteristicaMockMvc.perform(get("/api/propriedade-caracteristicas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(propriedadeCaracteristica.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }
    
    @Test
    @Transactional
    public void getPropriedade_caracteristica() throws Exception {
        // Initialize the database
        propriedadeCaracteristicaRepository.saveAndFlush(propriedadeCaracteristica);

        // Get the propriedadeCaracteristica
        restPropriedadeCaracteristicaMockMvc.perform(get("/api/propriedade-caracteristicas/{id}", propriedadeCaracteristica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(propriedadeCaracteristica.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getAllPropriedadeCaracteristicasByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        propriedadeCaracteristicaRepository.saveAndFlush(propriedadeCaracteristica);

        // Get all the propriedadeCaracteristicaList where value equals to DEFAULT_VALUE
        defaultPropriedade_caracteristicaShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the propriedadeCaracteristicaList where value equals to UPDATED_VALUE
        defaultPropriedade_caracteristicaShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllPropriedadeCaracteristicasByValueIsInShouldWork() throws Exception {
        // Initialize the database
        propriedadeCaracteristicaRepository.saveAndFlush(propriedadeCaracteristica);

        // Get all the propriedadeCaracteristicaList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultPropriedade_caracteristicaShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the propriedadeCaracteristicaList where value equals to UPDATED_VALUE
        defaultPropriedade_caracteristicaShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllPropriedadeCaracteristicasByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        propriedadeCaracteristicaRepository.saveAndFlush(propriedadeCaracteristica);

        // Get all the propriedadeCaracteristicaList where value is not null
        defaultPropriedade_caracteristicaShouldBeFound("value.specified=true");

        // Get all the propriedadeCaracteristicaList where value is null
        defaultPropriedade_caracteristicaShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    public void getAllPropriedadeCaracteristicasByPropriedadeIsEqualToSomething() throws Exception {
        // Get already existing entity
        Propriedade propriedade = propriedadeCaracteristica.getPropriedade();
        propriedadeCaracteristicaRepository.saveAndFlush(propriedadeCaracteristica);
        Long propriedadeId = propriedade.getId();

        // Get all the propriedadeCaracteristicaList where propriedade equals to propriedadeId
        defaultPropriedade_caracteristicaShouldBeFound("propriedadeId.equals=" + propriedadeId);

        // Get all the propriedadeCaracteristicaList where propriedade equals to propriedadeId + 1
        defaultPropriedade_caracteristicaShouldNotBeFound("propriedadeId.equals=" + (propriedadeId + 1));
    }


    @Test
    @Transactional
    public void getAllPropriedadeCaracteristicasByCaracteristicaIsEqualToSomething() throws Exception {
        // Get already existing entity
        Caracteristica caracteristica = propriedadeCaracteristica.getCaracteristica();
        propriedadeCaracteristicaRepository.saveAndFlush(propriedadeCaracteristica);
        Long caracteristicaId = caracteristica.getId();

        // Get all the propriedadeCaracteristicaList where caracteristica equals to caracteristicaId
        defaultPropriedade_caracteristicaShouldBeFound("caracteristicaId.equals=" + caracteristicaId);

        // Get all the propriedadeCaracteristicaList where caracteristica equals to caracteristicaId + 1
        defaultPropriedade_caracteristicaShouldNotBeFound("caracteristicaId.equals=" + (caracteristicaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPropriedade_caracteristicaShouldBeFound(String filter) throws Exception {
        restPropriedadeCaracteristicaMockMvc.perform(get("/api/propriedade-caracteristicas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(propriedadeCaracteristica.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));

        // Check, that the count call also returns 1
        restPropriedadeCaracteristicaMockMvc.perform(get("/api/propriedade-caracteristicas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPropriedade_caracteristicaShouldNotBeFound(String filter) throws Exception {
        restPropriedadeCaracteristicaMockMvc.perform(get("/api/propriedade-caracteristicas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPropriedadeCaracteristicaMockMvc.perform(get("/api/propriedade-caracteristicas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPropriedade_caracteristica() throws Exception {
        // Get the propriedadeCaracteristica
        restPropriedadeCaracteristicaMockMvc.perform(get("/api/propriedade-caracteristicas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePropriedade_caracteristica() throws Exception {
        // Initialize the database
        propriedadeCaracteristicaRepository.saveAndFlush(propriedadeCaracteristica);

        int databaseSizeBeforeUpdate = propriedadeCaracteristicaRepository.findAll().size();

        // Update the propriedadeCaracteristica
        PropriedadeCaracteristica updatedPropriedade_caracteristica = propriedadeCaracteristicaRepository.findById(propriedadeCaracteristica.getId()).get();
        // Disconnect from session so that the updates on updatedPropriedade_caracteristica are not directly saved in db
        em.detach(updatedPropriedade_caracteristica);
        updatedPropriedade_caracteristica
            .value(UPDATED_VALUE);
        PropriedadeCaracteristicaDTO propriedadeCaracteristicaDTO = propriedadeCaracteristicaMapper.toDto(updatedPropriedade_caracteristica);

        restPropriedadeCaracteristicaMockMvc.perform(put("/api/propriedade-caracteristicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propriedadeCaracteristicaDTO)))
            .andExpect(status().isOk());

        // Validate the PropriedadeCaracteristica in the database
        List<PropriedadeCaracteristica> propriedadeCaracteristicaList = propriedadeCaracteristicaRepository.findAll();
        assertThat(propriedadeCaracteristicaList).hasSize(databaseSizeBeforeUpdate);
        PropriedadeCaracteristica testPropriedade_caracteristica = propriedadeCaracteristicaList.get(propriedadeCaracteristicaList.size() - 1);
        assertThat(testPropriedade_caracteristica.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingPropriedade_caracteristica() throws Exception {
        int databaseSizeBeforeUpdate = propriedadeCaracteristicaRepository.findAll().size();

        // Create the PropriedadeCaracteristica
        PropriedadeCaracteristicaDTO propriedadeCaracteristicaDTO = propriedadeCaracteristicaMapper.toDto(propriedadeCaracteristica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPropriedadeCaracteristicaMockMvc.perform(put("/api/propriedade-caracteristicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propriedadeCaracteristicaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PropriedadeCaracteristica in the database
        List<PropriedadeCaracteristica> propriedadeCaracteristicaList = propriedadeCaracteristicaRepository.findAll();
        assertThat(propriedadeCaracteristicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePropriedade_caracteristica() throws Exception {
        // Initialize the database
        propriedadeCaracteristicaRepository.saveAndFlush(propriedadeCaracteristica);

        int databaseSizeBeforeDelete = propriedadeCaracteristicaRepository.findAll().size();

        // Delete the propriedadeCaracteristica
        restPropriedadeCaracteristicaMockMvc.perform(delete("/api/propriedade-caracteristicas/{id}", propriedadeCaracteristica.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<PropriedadeCaracteristica> propriedadeCaracteristicaList = propriedadeCaracteristicaRepository.findAll();
        assertThat(propriedadeCaracteristicaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PropriedadeCaracteristica.class);
        PropriedadeCaracteristica propriedadeCaracteristica1 = new PropriedadeCaracteristica();
        propriedadeCaracteristica1.setId(1L);
        PropriedadeCaracteristica propriedadeCaracteristica2 = new PropriedadeCaracteristica();
        propriedadeCaracteristica2.setId(propriedadeCaracteristica1.getId());
        assertThat(propriedadeCaracteristica1).isEqualTo(propriedadeCaracteristica2);
        propriedadeCaracteristica2.setId(2L);
        assertThat(propriedadeCaracteristica1).isNotEqualTo(propriedadeCaracteristica2);
        propriedadeCaracteristica1.setId(null);
        assertThat(propriedadeCaracteristica1).isNotEqualTo(propriedadeCaracteristica2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PropriedadeCaracteristicaDTO.class);
        PropriedadeCaracteristicaDTO propriedadeCaracteristicaDTO1 = new PropriedadeCaracteristicaDTO();
        propriedadeCaracteristicaDTO1.setId(1L);
        PropriedadeCaracteristicaDTO propriedadeCaracteristicaDTO2 = new PropriedadeCaracteristicaDTO();
        assertThat(propriedadeCaracteristicaDTO1).isNotEqualTo(propriedadeCaracteristicaDTO2);
        propriedadeCaracteristicaDTO2.setId(propriedadeCaracteristicaDTO1.getId());
        assertThat(propriedadeCaracteristicaDTO1).isEqualTo(propriedadeCaracteristicaDTO2);
        propriedadeCaracteristicaDTO2.setId(2L);
        assertThat(propriedadeCaracteristicaDTO1).isNotEqualTo(propriedadeCaracteristicaDTO2);
        propriedadeCaracteristicaDTO1.setId(null);
        assertThat(propriedadeCaracteristicaDTO1).isNotEqualTo(propriedadeCaracteristicaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(propriedadeCaracteristicaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(propriedadeCaracteristicaMapper.fromId(null)).isNull();
    }
}

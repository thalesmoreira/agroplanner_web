package br.com.agroplanner.web.rest;

import br.com.agroplanner.AgroplannerApp;
import br.com.agroplanner.domain.Propriedade_caracteristica;
import br.com.agroplanner.domain.Propriedade;
import br.com.agroplanner.domain.Caracteristica;
import br.com.agroplanner.repository.Propriedade_caracteristicaRepository;
import br.com.agroplanner.service.Propriedade_caracteristicaService;
import br.com.agroplanner.service.dto.Propriedade_caracteristicaDTO;
import br.com.agroplanner.service.mapper.Propriedade_caracteristicaMapper;
import br.com.agroplanner.web.rest.errors.ExceptionTranslator;
import br.com.agroplanner.service.dto.Propriedade_caracteristicaCriteria;
import br.com.agroplanner.service.Propriedade_caracteristicaQueryService;

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
public class Propriedade_caracteristicaResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private Propriedade_caracteristicaRepository propriedade_caracteristicaRepository;

    @Autowired
    private Propriedade_caracteristicaMapper propriedade_caracteristicaMapper;

    @Autowired
    private Propriedade_caracteristicaService propriedade_caracteristicaService;

    @Autowired
    private Propriedade_caracteristicaQueryService propriedade_caracteristicaQueryService;

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

    private MockMvc restPropriedade_caracteristicaMockMvc;

    private Propriedade_caracteristica propriedade_caracteristica;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Propriedade_caracteristicaResource propriedade_caracteristicaResource = new Propriedade_caracteristicaResource(propriedade_caracteristicaService, propriedade_caracteristicaQueryService);
        this.restPropriedade_caracteristicaMockMvc = MockMvcBuilders.standaloneSetup(propriedade_caracteristicaResource)
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
    public static Propriedade_caracteristica createEntity(EntityManager em) {
        Propriedade_caracteristica propriedade_caracteristica = new Propriedade_caracteristica()
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
        propriedade_caracteristica.setPropriedade(propriedade);
        // Add required entity
        Caracteristica caracteristica;
        if (TestUtil.findAll(em, Caracteristica.class).isEmpty()) {
            caracteristica = CaracteristicaResourceIT.createEntity(em);
            em.persist(caracteristica);
            em.flush();
        } else {
            caracteristica = TestUtil.findAll(em, Caracteristica.class).get(0);
        }
        propriedade_caracteristica.setCaracteristica(caracteristica);
        return propriedade_caracteristica;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Propriedade_caracteristica createUpdatedEntity(EntityManager em) {
        Propriedade_caracteristica propriedade_caracteristica = new Propriedade_caracteristica()
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
        propriedade_caracteristica.setPropriedade(propriedade);
        // Add required entity
        Caracteristica caracteristica;
        if (TestUtil.findAll(em, Caracteristica.class).isEmpty()) {
            caracteristica = CaracteristicaResourceIT.createUpdatedEntity(em);
            em.persist(caracteristica);
            em.flush();
        } else {
            caracteristica = TestUtil.findAll(em, Caracteristica.class).get(0);
        }
        propriedade_caracteristica.setCaracteristica(caracteristica);
        return propriedade_caracteristica;
    }

    @BeforeEach
    public void initTest() {
        propriedade_caracteristica = createEntity(em);
    }

    @Test
    @Transactional
    public void createPropriedade_caracteristica() throws Exception {
        int databaseSizeBeforeCreate = propriedade_caracteristicaRepository.findAll().size();

        // Create the Propriedade_caracteristica
        Propriedade_caracteristicaDTO propriedade_caracteristicaDTO = propriedade_caracteristicaMapper.toDto(propriedade_caracteristica);
        restPropriedade_caracteristicaMockMvc.perform(post("/api/propriedade-caracteristicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propriedade_caracteristicaDTO)))
            .andExpect(status().isCreated());

        // Validate the Propriedade_caracteristica in the database
        List<Propriedade_caracteristica> propriedade_caracteristicaList = propriedade_caracteristicaRepository.findAll();
        assertThat(propriedade_caracteristicaList).hasSize(databaseSizeBeforeCreate + 1);
        Propriedade_caracteristica testPropriedade_caracteristica = propriedade_caracteristicaList.get(propriedade_caracteristicaList.size() - 1);
        assertThat(testPropriedade_caracteristica.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createPropriedade_caracteristicaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = propriedade_caracteristicaRepository.findAll().size();

        // Create the Propriedade_caracteristica with an existing ID
        propriedade_caracteristica.setId(1L);
        Propriedade_caracteristicaDTO propriedade_caracteristicaDTO = propriedade_caracteristicaMapper.toDto(propriedade_caracteristica);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPropriedade_caracteristicaMockMvc.perform(post("/api/propriedade-caracteristicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propriedade_caracteristicaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Propriedade_caracteristica in the database
        List<Propriedade_caracteristica> propriedade_caracteristicaList = propriedade_caracteristicaRepository.findAll();
        assertThat(propriedade_caracteristicaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPropriedade_caracteristicas() throws Exception {
        // Initialize the database
        propriedade_caracteristicaRepository.saveAndFlush(propriedade_caracteristica);

        // Get all the propriedade_caracteristicaList
        restPropriedade_caracteristicaMockMvc.perform(get("/api/propriedade-caracteristicas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(propriedade_caracteristica.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }
    
    @Test
    @Transactional
    public void getPropriedade_caracteristica() throws Exception {
        // Initialize the database
        propriedade_caracteristicaRepository.saveAndFlush(propriedade_caracteristica);

        // Get the propriedade_caracteristica
        restPropriedade_caracteristicaMockMvc.perform(get("/api/propriedade-caracteristicas/{id}", propriedade_caracteristica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(propriedade_caracteristica.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getAllPropriedade_caracteristicasByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        propriedade_caracteristicaRepository.saveAndFlush(propriedade_caracteristica);

        // Get all the propriedade_caracteristicaList where value equals to DEFAULT_VALUE
        defaultPropriedade_caracteristicaShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the propriedade_caracteristicaList where value equals to UPDATED_VALUE
        defaultPropriedade_caracteristicaShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllPropriedade_caracteristicasByValueIsInShouldWork() throws Exception {
        // Initialize the database
        propriedade_caracteristicaRepository.saveAndFlush(propriedade_caracteristica);

        // Get all the propriedade_caracteristicaList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultPropriedade_caracteristicaShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the propriedade_caracteristicaList where value equals to UPDATED_VALUE
        defaultPropriedade_caracteristicaShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllPropriedade_caracteristicasByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        propriedade_caracteristicaRepository.saveAndFlush(propriedade_caracteristica);

        // Get all the propriedade_caracteristicaList where value is not null
        defaultPropriedade_caracteristicaShouldBeFound("value.specified=true");

        // Get all the propriedade_caracteristicaList where value is null
        defaultPropriedade_caracteristicaShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    public void getAllPropriedade_caracteristicasByPropriedadeIsEqualToSomething() throws Exception {
        // Get already existing entity
        Propriedade propriedade = propriedade_caracteristica.getPropriedade();
        propriedade_caracteristicaRepository.saveAndFlush(propriedade_caracteristica);
        Long propriedadeId = propriedade.getId();

        // Get all the propriedade_caracteristicaList where propriedade equals to propriedadeId
        defaultPropriedade_caracteristicaShouldBeFound("propriedadeId.equals=" + propriedadeId);

        // Get all the propriedade_caracteristicaList where propriedade equals to propriedadeId + 1
        defaultPropriedade_caracteristicaShouldNotBeFound("propriedadeId.equals=" + (propriedadeId + 1));
    }


    @Test
    @Transactional
    public void getAllPropriedade_caracteristicasByCaracteristicaIsEqualToSomething() throws Exception {
        // Get already existing entity
        Caracteristica caracteristica = propriedade_caracteristica.getCaracteristica();
        propriedade_caracteristicaRepository.saveAndFlush(propriedade_caracteristica);
        Long caracteristicaId = caracteristica.getId();

        // Get all the propriedade_caracteristicaList where caracteristica equals to caracteristicaId
        defaultPropriedade_caracteristicaShouldBeFound("caracteristicaId.equals=" + caracteristicaId);

        // Get all the propriedade_caracteristicaList where caracteristica equals to caracteristicaId + 1
        defaultPropriedade_caracteristicaShouldNotBeFound("caracteristicaId.equals=" + (caracteristicaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPropriedade_caracteristicaShouldBeFound(String filter) throws Exception {
        restPropriedade_caracteristicaMockMvc.perform(get("/api/propriedade-caracteristicas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(propriedade_caracteristica.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));

        // Check, that the count call also returns 1
        restPropriedade_caracteristicaMockMvc.perform(get("/api/propriedade-caracteristicas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPropriedade_caracteristicaShouldNotBeFound(String filter) throws Exception {
        restPropriedade_caracteristicaMockMvc.perform(get("/api/propriedade-caracteristicas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPropriedade_caracteristicaMockMvc.perform(get("/api/propriedade-caracteristicas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPropriedade_caracteristica() throws Exception {
        // Get the propriedade_caracteristica
        restPropriedade_caracteristicaMockMvc.perform(get("/api/propriedade-caracteristicas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePropriedade_caracteristica() throws Exception {
        // Initialize the database
        propriedade_caracteristicaRepository.saveAndFlush(propriedade_caracteristica);

        int databaseSizeBeforeUpdate = propriedade_caracteristicaRepository.findAll().size();

        // Update the propriedade_caracteristica
        Propriedade_caracteristica updatedPropriedade_caracteristica = propriedade_caracteristicaRepository.findById(propriedade_caracteristica.getId()).get();
        // Disconnect from session so that the updates on updatedPropriedade_caracteristica are not directly saved in db
        em.detach(updatedPropriedade_caracteristica);
        updatedPropriedade_caracteristica
            .value(UPDATED_VALUE);
        Propriedade_caracteristicaDTO propriedade_caracteristicaDTO = propriedade_caracteristicaMapper.toDto(updatedPropriedade_caracteristica);

        restPropriedade_caracteristicaMockMvc.perform(put("/api/propriedade-caracteristicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propriedade_caracteristicaDTO)))
            .andExpect(status().isOk());

        // Validate the Propriedade_caracteristica in the database
        List<Propriedade_caracteristica> propriedade_caracteristicaList = propriedade_caracteristicaRepository.findAll();
        assertThat(propriedade_caracteristicaList).hasSize(databaseSizeBeforeUpdate);
        Propriedade_caracteristica testPropriedade_caracteristica = propriedade_caracteristicaList.get(propriedade_caracteristicaList.size() - 1);
        assertThat(testPropriedade_caracteristica.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingPropriedade_caracteristica() throws Exception {
        int databaseSizeBeforeUpdate = propriedade_caracteristicaRepository.findAll().size();

        // Create the Propriedade_caracteristica
        Propriedade_caracteristicaDTO propriedade_caracteristicaDTO = propriedade_caracteristicaMapper.toDto(propriedade_caracteristica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPropriedade_caracteristicaMockMvc.perform(put("/api/propriedade-caracteristicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propriedade_caracteristicaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Propriedade_caracteristica in the database
        List<Propriedade_caracteristica> propriedade_caracteristicaList = propriedade_caracteristicaRepository.findAll();
        assertThat(propriedade_caracteristicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePropriedade_caracteristica() throws Exception {
        // Initialize the database
        propriedade_caracteristicaRepository.saveAndFlush(propriedade_caracteristica);

        int databaseSizeBeforeDelete = propriedade_caracteristicaRepository.findAll().size();

        // Delete the propriedade_caracteristica
        restPropriedade_caracteristicaMockMvc.perform(delete("/api/propriedade-caracteristicas/{id}", propriedade_caracteristica.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Propriedade_caracteristica> propriedade_caracteristicaList = propriedade_caracteristicaRepository.findAll();
        assertThat(propriedade_caracteristicaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Propriedade_caracteristica.class);
        Propriedade_caracteristica propriedade_caracteristica1 = new Propriedade_caracteristica();
        propriedade_caracteristica1.setId(1L);
        Propriedade_caracteristica propriedade_caracteristica2 = new Propriedade_caracteristica();
        propriedade_caracteristica2.setId(propriedade_caracteristica1.getId());
        assertThat(propriedade_caracteristica1).isEqualTo(propriedade_caracteristica2);
        propriedade_caracteristica2.setId(2L);
        assertThat(propriedade_caracteristica1).isNotEqualTo(propriedade_caracteristica2);
        propriedade_caracteristica1.setId(null);
        assertThat(propriedade_caracteristica1).isNotEqualTo(propriedade_caracteristica2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Propriedade_caracteristicaDTO.class);
        Propriedade_caracteristicaDTO propriedade_caracteristicaDTO1 = new Propriedade_caracteristicaDTO();
        propriedade_caracteristicaDTO1.setId(1L);
        Propriedade_caracteristicaDTO propriedade_caracteristicaDTO2 = new Propriedade_caracteristicaDTO();
        assertThat(propriedade_caracteristicaDTO1).isNotEqualTo(propriedade_caracteristicaDTO2);
        propriedade_caracteristicaDTO2.setId(propriedade_caracteristicaDTO1.getId());
        assertThat(propriedade_caracteristicaDTO1).isEqualTo(propriedade_caracteristicaDTO2);
        propriedade_caracteristicaDTO2.setId(2L);
        assertThat(propriedade_caracteristicaDTO1).isNotEqualTo(propriedade_caracteristicaDTO2);
        propriedade_caracteristicaDTO1.setId(null);
        assertThat(propriedade_caracteristicaDTO1).isNotEqualTo(propriedade_caracteristicaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(propriedade_caracteristicaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(propriedade_caracteristicaMapper.fromId(null)).isNull();
    }
}

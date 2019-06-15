package br.com.agroplanner.web.rest;

import br.com.agroplanner.AgroplannerApp;
import br.com.agroplanner.domain.Caracteristica;
import br.com.agroplanner.repository.CaracteristicaRepository;
import br.com.agroplanner.service.CaracteristicaService;
import br.com.agroplanner.service.dto.CaracteristicaDTO;
import br.com.agroplanner.service.mapper.CaracteristicaMapper;
import br.com.agroplanner.web.rest.errors.ExceptionTranslator;
import br.com.agroplanner.service.dto.CaracteristicaCriteria;
import br.com.agroplanner.service.CaracteristicaQueryService;

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
 * Integration tests for the {@Link CaracteristicaResource} REST controller.
 */
@SpringBootTest(classes = AgroplannerApp.class)
public class CaracteristicaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private CaracteristicaRepository caracteristicaRepository;

    @Autowired
    private CaracteristicaMapper caracteristicaMapper;

    @Autowired
    private CaracteristicaService caracteristicaService;

    @Autowired
    private CaracteristicaQueryService caracteristicaQueryService;

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

    private MockMvc restCaracteristicaMockMvc;

    private Caracteristica caracteristica;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CaracteristicaResource caracteristicaResource = new CaracteristicaResource(caracteristicaService, caracteristicaQueryService);
        this.restCaracteristicaMockMvc = MockMvcBuilders.standaloneSetup(caracteristicaResource)
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
    public static Caracteristica createEntity(EntityManager em) {
        Caracteristica caracteristica = new Caracteristica()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO);
        return caracteristica;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Caracteristica createUpdatedEntity(EntityManager em) {
        Caracteristica caracteristica = new Caracteristica()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO);
        return caracteristica;
    }

    @BeforeEach
    public void initTest() {
        caracteristica = createEntity(em);
    }

    @Test
    @Transactional
    public void createCaracteristica() throws Exception {
        int databaseSizeBeforeCreate = caracteristicaRepository.findAll().size();

        // Create the Caracteristica
        CaracteristicaDTO caracteristicaDTO = caracteristicaMapper.toDto(caracteristica);
        restCaracteristicaMockMvc.perform(post("/api/caracteristicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caracteristicaDTO)))
            .andExpect(status().isCreated());

        // Validate the Caracteristica in the database
        List<Caracteristica> caracteristicaList = caracteristicaRepository.findAll();
        assertThat(caracteristicaList).hasSize(databaseSizeBeforeCreate + 1);
        Caracteristica testCaracteristica = caracteristicaList.get(caracteristicaList.size() - 1);
        assertThat(testCaracteristica.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testCaracteristica.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createCaracteristicaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = caracteristicaRepository.findAll().size();

        // Create the Caracteristica with an existing ID
        caracteristica.setId(1L);
        CaracteristicaDTO caracteristicaDTO = caracteristicaMapper.toDto(caracteristica);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCaracteristicaMockMvc.perform(post("/api/caracteristicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caracteristicaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Caracteristica in the database
        List<Caracteristica> caracteristicaList = caracteristicaRepository.findAll();
        assertThat(caracteristicaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = caracteristicaRepository.findAll().size();
        // set the field null
        caracteristica.setNome(null);

        // Create the Caracteristica, which fails.
        CaracteristicaDTO caracteristicaDTO = caracteristicaMapper.toDto(caracteristica);

        restCaracteristicaMockMvc.perform(post("/api/caracteristicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caracteristicaDTO)))
            .andExpect(status().isBadRequest());

        List<Caracteristica> caracteristicaList = caracteristicaRepository.findAll();
        assertThat(caracteristicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCaracteristicas() throws Exception {
        // Initialize the database
        caracteristicaRepository.saveAndFlush(caracteristica);

        // Get all the caracteristicaList
        restCaracteristicaMockMvc.perform(get("/api/caracteristicas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caracteristica.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }
    
    @Test
    @Transactional
    public void getCaracteristica() throws Exception {
        // Initialize the database
        caracteristicaRepository.saveAndFlush(caracteristica);

        // Get the caracteristica
        restCaracteristicaMockMvc.perform(get("/api/caracteristicas/{id}", caracteristica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(caracteristica.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    public void getAllCaracteristicasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        caracteristicaRepository.saveAndFlush(caracteristica);

        // Get all the caracteristicaList where nome equals to DEFAULT_NOME
        defaultCaracteristicaShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the caracteristicaList where nome equals to UPDATED_NOME
        defaultCaracteristicaShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllCaracteristicasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        caracteristicaRepository.saveAndFlush(caracteristica);

        // Get all the caracteristicaList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultCaracteristicaShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the caracteristicaList where nome equals to UPDATED_NOME
        defaultCaracteristicaShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllCaracteristicasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        caracteristicaRepository.saveAndFlush(caracteristica);

        // Get all the caracteristicaList where nome is not null
        defaultCaracteristicaShouldBeFound("nome.specified=true");

        // Get all the caracteristicaList where nome is null
        defaultCaracteristicaShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    public void getAllCaracteristicasByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        caracteristicaRepository.saveAndFlush(caracteristica);

        // Get all the caracteristicaList where descricao equals to DEFAULT_DESCRICAO
        defaultCaracteristicaShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the caracteristicaList where descricao equals to UPDATED_DESCRICAO
        defaultCaracteristicaShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllCaracteristicasByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        caracteristicaRepository.saveAndFlush(caracteristica);

        // Get all the caracteristicaList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultCaracteristicaShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the caracteristicaList where descricao equals to UPDATED_DESCRICAO
        defaultCaracteristicaShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllCaracteristicasByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        caracteristicaRepository.saveAndFlush(caracteristica);

        // Get all the caracteristicaList where descricao is not null
        defaultCaracteristicaShouldBeFound("descricao.specified=true");

        // Get all the caracteristicaList where descricao is null
        defaultCaracteristicaShouldNotBeFound("descricao.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCaracteristicaShouldBeFound(String filter) throws Exception {
        restCaracteristicaMockMvc.perform(get("/api/caracteristicas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caracteristica.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));

        // Check, that the count call also returns 1
        restCaracteristicaMockMvc.perform(get("/api/caracteristicas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCaracteristicaShouldNotBeFound(String filter) throws Exception {
        restCaracteristicaMockMvc.perform(get("/api/caracteristicas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCaracteristicaMockMvc.perform(get("/api/caracteristicas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCaracteristica() throws Exception {
        // Get the caracteristica
        restCaracteristicaMockMvc.perform(get("/api/caracteristicas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCaracteristica() throws Exception {
        // Initialize the database
        caracteristicaRepository.saveAndFlush(caracteristica);

        int databaseSizeBeforeUpdate = caracteristicaRepository.findAll().size();

        // Update the caracteristica
        Caracteristica updatedCaracteristica = caracteristicaRepository.findById(caracteristica.getId()).get();
        // Disconnect from session so that the updates on updatedCaracteristica are not directly saved in db
        em.detach(updatedCaracteristica);
        updatedCaracteristica
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO);
        CaracteristicaDTO caracteristicaDTO = caracteristicaMapper.toDto(updatedCaracteristica);

        restCaracteristicaMockMvc.perform(put("/api/caracteristicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caracteristicaDTO)))
            .andExpect(status().isOk());

        // Validate the Caracteristica in the database
        List<Caracteristica> caracteristicaList = caracteristicaRepository.findAll();
        assertThat(caracteristicaList).hasSize(databaseSizeBeforeUpdate);
        Caracteristica testCaracteristica = caracteristicaList.get(caracteristicaList.size() - 1);
        assertThat(testCaracteristica.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCaracteristica.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingCaracteristica() throws Exception {
        int databaseSizeBeforeUpdate = caracteristicaRepository.findAll().size();

        // Create the Caracteristica
        CaracteristicaDTO caracteristicaDTO = caracteristicaMapper.toDto(caracteristica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCaracteristicaMockMvc.perform(put("/api/caracteristicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caracteristicaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Caracteristica in the database
        List<Caracteristica> caracteristicaList = caracteristicaRepository.findAll();
        assertThat(caracteristicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCaracteristica() throws Exception {
        // Initialize the database
        caracteristicaRepository.saveAndFlush(caracteristica);

        int databaseSizeBeforeDelete = caracteristicaRepository.findAll().size();

        // Delete the caracteristica
        restCaracteristicaMockMvc.perform(delete("/api/caracteristicas/{id}", caracteristica.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Caracteristica> caracteristicaList = caracteristicaRepository.findAll();
        assertThat(caracteristicaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Caracteristica.class);
        Caracteristica caracteristica1 = new Caracteristica();
        caracteristica1.setId(1L);
        Caracteristica caracteristica2 = new Caracteristica();
        caracteristica2.setId(caracteristica1.getId());
        assertThat(caracteristica1).isEqualTo(caracteristica2);
        caracteristica2.setId(2L);
        assertThat(caracteristica1).isNotEqualTo(caracteristica2);
        caracteristica1.setId(null);
        assertThat(caracteristica1).isNotEqualTo(caracteristica2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CaracteristicaDTO.class);
        CaracteristicaDTO caracteristicaDTO1 = new CaracteristicaDTO();
        caracteristicaDTO1.setId(1L);
        CaracteristicaDTO caracteristicaDTO2 = new CaracteristicaDTO();
        assertThat(caracteristicaDTO1).isNotEqualTo(caracteristicaDTO2);
        caracteristicaDTO2.setId(caracteristicaDTO1.getId());
        assertThat(caracteristicaDTO1).isEqualTo(caracteristicaDTO2);
        caracteristicaDTO2.setId(2L);
        assertThat(caracteristicaDTO1).isNotEqualTo(caracteristicaDTO2);
        caracteristicaDTO1.setId(null);
        assertThat(caracteristicaDTO1).isNotEqualTo(caracteristicaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(caracteristicaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(caracteristicaMapper.fromId(null)).isNull();
    }
}

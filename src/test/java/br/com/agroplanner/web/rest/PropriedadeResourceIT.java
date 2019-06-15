package br.com.agroplanner.web.rest;

import br.com.agroplanner.AgroplannerApp;
import br.com.agroplanner.domain.Propriedade;
import br.com.agroplanner.domain.User;
import br.com.agroplanner.repository.PropriedadeRepository;
import br.com.agroplanner.service.PropriedadeService;
import br.com.agroplanner.service.dto.PropriedadeDTO;
import br.com.agroplanner.service.mapper.PropriedadeMapper;
import br.com.agroplanner.web.rest.errors.ExceptionTranslator;
import br.com.agroplanner.service.dto.PropriedadeCriteria;
import br.com.agroplanner.service.PropriedadeQueryService;

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
 * Integration tests for the {@Link PropriedadeResource} REST controller.
 */
@SpringBootTest(classes = AgroplannerApp.class)
public class PropriedadeResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALIDADE = "AAAAAAAAAA";
    private static final String UPDATED_LOCALIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_GEOREFERENCIAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_GEOREFERENCIAMENTO = "BBBBBBBBBB";

    @Autowired
    private PropriedadeRepository propriedadeRepository;

    @Autowired
    private PropriedadeMapper propriedadeMapper;

    @Autowired
    private PropriedadeService propriedadeService;

    @Autowired
    private PropriedadeQueryService propriedadeQueryService;

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

    private MockMvc restPropriedadeMockMvc;

    private Propriedade propriedade;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PropriedadeResource propriedadeResource = new PropriedadeResource(propriedadeService, propriedadeQueryService);
        this.restPropriedadeMockMvc = MockMvcBuilders.standaloneSetup(propriedadeResource)
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
    public static Propriedade createEntity(EntityManager em) {
        Propriedade propriedade = new Propriedade()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .localidade(DEFAULT_LOCALIDADE)
            .georeferenciamento(DEFAULT_GEOREFERENCIAMENTO);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        propriedade.setUser(user);
        return propriedade;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Propriedade createUpdatedEntity(EntityManager em) {
        Propriedade propriedade = new Propriedade()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .localidade(UPDATED_LOCALIDADE)
            .georeferenciamento(UPDATED_GEOREFERENCIAMENTO);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        propriedade.setUser(user);
        return propriedade;
    }

    @BeforeEach
    public void initTest() {
        propriedade = createEntity(em);
    }

    @Test
    @Transactional
    public void createPropriedade() throws Exception {
        int databaseSizeBeforeCreate = propriedadeRepository.findAll().size();

        // Create the Propriedade
        PropriedadeDTO propriedadeDTO = propriedadeMapper.toDto(propriedade);
        restPropriedadeMockMvc.perform(post("/api/propriedades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propriedadeDTO)))
            .andExpect(status().isCreated());

        // Validate the Propriedade in the database
        List<Propriedade> propriedadeList = propriedadeRepository.findAll();
        assertThat(propriedadeList).hasSize(databaseSizeBeforeCreate + 1);
        Propriedade testPropriedade = propriedadeList.get(propriedadeList.size() - 1);
        assertThat(testPropriedade.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testPropriedade.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testPropriedade.getLocalidade()).isEqualTo(DEFAULT_LOCALIDADE);
        assertThat(testPropriedade.getGeoreferenciamento()).isEqualTo(DEFAULT_GEOREFERENCIAMENTO);
    }

    @Test
    @Transactional
    public void createPropriedadeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = propriedadeRepository.findAll().size();

        // Create the Propriedade with an existing ID
        propriedade.setId(1L);
        PropriedadeDTO propriedadeDTO = propriedadeMapper.toDto(propriedade);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPropriedadeMockMvc.perform(post("/api/propriedades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propriedadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Propriedade in the database
        List<Propriedade> propriedadeList = propriedadeRepository.findAll();
        assertThat(propriedadeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = propriedadeRepository.findAll().size();
        // set the field null
        propriedade.setNome(null);

        // Create the Propriedade, which fails.
        PropriedadeDTO propriedadeDTO = propriedadeMapper.toDto(propriedade);

        restPropriedadeMockMvc.perform(post("/api/propriedades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propriedadeDTO)))
            .andExpect(status().isBadRequest());

        List<Propriedade> propriedadeList = propriedadeRepository.findAll();
        assertThat(propriedadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = propriedadeRepository.findAll().size();
        // set the field null
        propriedade.setDescricao(null);

        // Create the Propriedade, which fails.
        PropriedadeDTO propriedadeDTO = propriedadeMapper.toDto(propriedade);

        restPropriedadeMockMvc.perform(post("/api/propriedades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propriedadeDTO)))
            .andExpect(status().isBadRequest());

        List<Propriedade> propriedadeList = propriedadeRepository.findAll();
        assertThat(propriedadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocalidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = propriedadeRepository.findAll().size();
        // set the field null
        propriedade.setLocalidade(null);

        // Create the Propriedade, which fails.
        PropriedadeDTO propriedadeDTO = propriedadeMapper.toDto(propriedade);

        restPropriedadeMockMvc.perform(post("/api/propriedades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propriedadeDTO)))
            .andExpect(status().isBadRequest());

        List<Propriedade> propriedadeList = propriedadeRepository.findAll();
        assertThat(propriedadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPropriedades() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList
        restPropriedadeMockMvc.perform(get("/api/propriedades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(propriedade.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].localidade").value(hasItem(DEFAULT_LOCALIDADE.toString())))
            .andExpect(jsonPath("$.[*].georeferenciamento").value(hasItem(DEFAULT_GEOREFERENCIAMENTO.toString())));
    }
    
    @Test
    @Transactional
    public void getPropriedade() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get the propriedade
        restPropriedadeMockMvc.perform(get("/api/propriedades/{id}", propriedade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(propriedade.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.localidade").value(DEFAULT_LOCALIDADE.toString()))
            .andExpect(jsonPath("$.georeferenciamento").value(DEFAULT_GEOREFERENCIAMENTO.toString()));
    }

    @Test
    @Transactional
    public void getAllPropriedadesByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where nome equals to DEFAULT_NOME
        defaultPropriedadeShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the propriedadeList where nome equals to UPDATED_NOME
        defaultPropriedadeShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllPropriedadesByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultPropriedadeShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the propriedadeList where nome equals to UPDATED_NOME
        defaultPropriedadeShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllPropriedadesByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where nome is not null
        defaultPropriedadeShouldBeFound("nome.specified=true");

        // Get all the propriedadeList where nome is null
        defaultPropriedadeShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    public void getAllPropriedadesByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where descricao equals to DEFAULT_DESCRICAO
        defaultPropriedadeShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the propriedadeList where descricao equals to UPDATED_DESCRICAO
        defaultPropriedadeShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPropriedadesByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultPropriedadeShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the propriedadeList where descricao equals to UPDATED_DESCRICAO
        defaultPropriedadeShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void getAllPropriedadesByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where descricao is not null
        defaultPropriedadeShouldBeFound("descricao.specified=true");

        // Get all the propriedadeList where descricao is null
        defaultPropriedadeShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    public void getAllPropriedadesByLocalidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where localidade equals to DEFAULT_LOCALIDADE
        defaultPropriedadeShouldBeFound("localidade.equals=" + DEFAULT_LOCALIDADE);

        // Get all the propriedadeList where localidade equals to UPDATED_LOCALIDADE
        defaultPropriedadeShouldNotBeFound("localidade.equals=" + UPDATED_LOCALIDADE);
    }

    @Test
    @Transactional
    public void getAllPropriedadesByLocalidadeIsInShouldWork() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where localidade in DEFAULT_LOCALIDADE or UPDATED_LOCALIDADE
        defaultPropriedadeShouldBeFound("localidade.in=" + DEFAULT_LOCALIDADE + "," + UPDATED_LOCALIDADE);

        // Get all the propriedadeList where localidade equals to UPDATED_LOCALIDADE
        defaultPropriedadeShouldNotBeFound("localidade.in=" + UPDATED_LOCALIDADE);
    }

    @Test
    @Transactional
    public void getAllPropriedadesByLocalidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        // Get all the propriedadeList where localidade is not null
        defaultPropriedadeShouldBeFound("localidade.specified=true");

        // Get all the propriedadeList where localidade is null
        defaultPropriedadeShouldNotBeFound("localidade.specified=false");
    }

    @Test
    @Transactional
    public void getAllPropriedadesByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = propriedade.getUser();
        propriedadeRepository.saveAndFlush(propriedade);
        Long userId = user.getId();

        // Get all the propriedadeList where user equals to userId
        defaultPropriedadeShouldBeFound("userId.equals=" + userId);

        // Get all the propriedadeList where user equals to userId + 1
        defaultPropriedadeShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPropriedadeShouldBeFound(String filter) throws Exception {
        restPropriedadeMockMvc.perform(get("/api/propriedades?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(propriedade.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].localidade").value(hasItem(DEFAULT_LOCALIDADE)))
            .andExpect(jsonPath("$.[*].georeferenciamento").value(hasItem(DEFAULT_GEOREFERENCIAMENTO.toString())));

        // Check, that the count call also returns 1
        restPropriedadeMockMvc.perform(get("/api/propriedades/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPropriedadeShouldNotBeFound(String filter) throws Exception {
        restPropriedadeMockMvc.perform(get("/api/propriedades?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPropriedadeMockMvc.perform(get("/api/propriedades/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPropriedade() throws Exception {
        // Get the propriedade
        restPropriedadeMockMvc.perform(get("/api/propriedades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePropriedade() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        int databaseSizeBeforeUpdate = propriedadeRepository.findAll().size();

        // Update the propriedade
        Propriedade updatedPropriedade = propriedadeRepository.findById(propriedade.getId()).get();
        // Disconnect from session so that the updates on updatedPropriedade are not directly saved in db
        em.detach(updatedPropriedade);
        updatedPropriedade
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .localidade(UPDATED_LOCALIDADE)
            .georeferenciamento(UPDATED_GEOREFERENCIAMENTO);
        PropriedadeDTO propriedadeDTO = propriedadeMapper.toDto(updatedPropriedade);

        restPropriedadeMockMvc.perform(put("/api/propriedades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propriedadeDTO)))
            .andExpect(status().isOk());

        // Validate the Propriedade in the database
        List<Propriedade> propriedadeList = propriedadeRepository.findAll();
        assertThat(propriedadeList).hasSize(databaseSizeBeforeUpdate);
        Propriedade testPropriedade = propriedadeList.get(propriedadeList.size() - 1);
        assertThat(testPropriedade.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPropriedade.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testPropriedade.getLocalidade()).isEqualTo(UPDATED_LOCALIDADE);
        assertThat(testPropriedade.getGeoreferenciamento()).isEqualTo(UPDATED_GEOREFERENCIAMENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingPropriedade() throws Exception {
        int databaseSizeBeforeUpdate = propriedadeRepository.findAll().size();

        // Create the Propriedade
        PropriedadeDTO propriedadeDTO = propriedadeMapper.toDto(propriedade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPropriedadeMockMvc.perform(put("/api/propriedades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propriedadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Propriedade in the database
        List<Propriedade> propriedadeList = propriedadeRepository.findAll();
        assertThat(propriedadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePropriedade() throws Exception {
        // Initialize the database
        propriedadeRepository.saveAndFlush(propriedade);

        int databaseSizeBeforeDelete = propriedadeRepository.findAll().size();

        // Delete the propriedade
        restPropriedadeMockMvc.perform(delete("/api/propriedades/{id}", propriedade.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Propriedade> propriedadeList = propriedadeRepository.findAll();
        assertThat(propriedadeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Propriedade.class);
        Propriedade propriedade1 = new Propriedade();
        propriedade1.setId(1L);
        Propriedade propriedade2 = new Propriedade();
        propriedade2.setId(propriedade1.getId());
        assertThat(propriedade1).isEqualTo(propriedade2);
        propriedade2.setId(2L);
        assertThat(propriedade1).isNotEqualTo(propriedade2);
        propriedade1.setId(null);
        assertThat(propriedade1).isNotEqualTo(propriedade2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PropriedadeDTO.class);
        PropriedadeDTO propriedadeDTO1 = new PropriedadeDTO();
        propriedadeDTO1.setId(1L);
        PropriedadeDTO propriedadeDTO2 = new PropriedadeDTO();
        assertThat(propriedadeDTO1).isNotEqualTo(propriedadeDTO2);
        propriedadeDTO2.setId(propriedadeDTO1.getId());
        assertThat(propriedadeDTO1).isEqualTo(propriedadeDTO2);
        propriedadeDTO2.setId(2L);
        assertThat(propriedadeDTO1).isNotEqualTo(propriedadeDTO2);
        propriedadeDTO1.setId(null);
        assertThat(propriedadeDTO1).isNotEqualTo(propriedadeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(propriedadeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(propriedadeMapper.fromId(null)).isNull();
    }
}

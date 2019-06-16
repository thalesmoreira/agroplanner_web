package br.com.agroplanner.web.rest;

import br.com.agroplanner.AgroplannerApp;
import br.com.agroplanner.domain.PropriedadeContratada;
import br.com.agroplanner.domain.Propriedade;
import br.com.agroplanner.domain.User;
import br.com.agroplanner.repository.PropriedadeContratadaRepository;
import br.com.agroplanner.service.PropriedadeContratadaService;
import br.com.agroplanner.service.dto.PropriedadeContratadaDTO;
import br.com.agroplanner.service.mapper.PropriedadeContratadaMapper;
import br.com.agroplanner.web.rest.errors.ExceptionTranslator;
import br.com.agroplanner.service.dto.PropriedadeContratadaCriteria;
import br.com.agroplanner.service.PropriedadeContratadaQueryService;

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
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static br.com.agroplanner.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.agroplanner.domain.enumeration.FormaDePagamento;
/**
 * Integration tests for the {@Link PropriedadeContratadaResource} REST controller.
 */
@SpringBootTest(classes = AgroplannerApp.class)
public class PropriedadeContratadaResourceIT {

    private static final Instant DEFAULT_DATA_INICIAL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_INICIAL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_FINAL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_FINAL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_QUANTIDADE_CABECAS = 1;
    private static final Integer UPDATED_QUANTIDADE_CABECAS = 2;

    private static final BigDecimal DEFAULT_VALOR_CONTRATADO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_CONTRATADO = new BigDecimal(2);

    private static final FormaDePagamento DEFAULT_FORMA_PAGAMENTO = FormaDePagamento.DEBITO;
    private static final FormaDePagamento UPDATED_FORMA_PAGAMENTO = FormaDePagamento.CREDITO;

    private static final Integer DEFAULT_PARCELAS = 1;
    private static final Integer UPDATED_PARCELAS = 2;

    private static final BigDecimal DEFAULT_VALOR_PARCELA = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_PARCELA = new BigDecimal(2);

    @Autowired
    private PropriedadeContratadaRepository propriedadeContratadaRepository;

    @Autowired
    private PropriedadeContratadaMapper propriedadeContratadaMapper;

    @Autowired
    private PropriedadeContratadaService propriedadeContratadaService;

    @Autowired
    private PropriedadeContratadaQueryService propriedadeContratadaQueryService;

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

    private MockMvc restPropriedadeContratadaMockMvc;

    private PropriedadeContratada propriedadeContratada;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PropriedadeContratadaResource propriedadeContratadaResource = new PropriedadeContratadaResource(propriedadeContratadaService, propriedadeContratadaQueryService);
        this.restPropriedadeContratadaMockMvc = MockMvcBuilders.standaloneSetup(propriedadeContratadaResource)
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
    public static PropriedadeContratada createEntity(EntityManager em) {
        PropriedadeContratada propriedadeContratada = new PropriedadeContratada()
            .dataInicial(DEFAULT_DATA_INICIAL)
            .dataFinal(DEFAULT_DATA_FINAL)
            .quantidadeCabecas(DEFAULT_QUANTIDADE_CABECAS)
            .valorContratado(DEFAULT_VALOR_CONTRATADO)
            .formaPagamento(DEFAULT_FORMA_PAGAMENTO)
            .parcelas(DEFAULT_PARCELAS)
            .valorParcela(DEFAULT_VALOR_PARCELA);
        // Add required entity
        Propriedade propriedade;
        if (TestUtil.findAll(em, Propriedade.class).isEmpty()) {
            propriedade = PropriedadeResourceIT.createEntity(em);
            em.persist(propriedade);
            em.flush();
        } else {
            propriedade = TestUtil.findAll(em, Propriedade.class).get(0);
        }
        propriedadeContratada.setPropriedade(propriedade);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        propriedadeContratada.setUser(user);
        return propriedadeContratada;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PropriedadeContratada createUpdatedEntity(EntityManager em) {
        PropriedadeContratada propriedadeContratada = new PropriedadeContratada()
            .dataInicial(UPDATED_DATA_INICIAL)
            .dataFinal(UPDATED_DATA_FINAL)
            .quantidadeCabecas(UPDATED_QUANTIDADE_CABECAS)
            .valorContratado(UPDATED_VALOR_CONTRATADO)
            .formaPagamento(UPDATED_FORMA_PAGAMENTO)
            .parcelas(UPDATED_PARCELAS)
            .valorParcela(UPDATED_VALOR_PARCELA);
        // Add required entity
        Propriedade propriedade;
        if (TestUtil.findAll(em, Propriedade.class).isEmpty()) {
            propriedade = PropriedadeResourceIT.createUpdatedEntity(em);
            em.persist(propriedade);
            em.flush();
        } else {
            propriedade = TestUtil.findAll(em, Propriedade.class).get(0);
        }
        propriedadeContratada.setPropriedade(propriedade);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        propriedadeContratada.setUser(user);
        return propriedadeContratada;
    }

    @BeforeEach
    public void initTest() {
        propriedadeContratada = createEntity(em);
    }

    @Test
    @Transactional
    public void createPropriedadeContratada() throws Exception {
        int databaseSizeBeforeCreate = propriedadeContratadaRepository.findAll().size();

        // Create the PropriedadeContratada
        PropriedadeContratadaDTO propriedadeContratadaDTO = propriedadeContratadaMapper.toDto(propriedadeContratada);
        restPropriedadeContratadaMockMvc.perform(post("/api/propriedade-contratadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propriedadeContratadaDTO)))
            .andExpect(status().isCreated());

        // Validate the PropriedadeContratada in the database
        List<PropriedadeContratada> propriedadeContratadaList = propriedadeContratadaRepository.findAll();
        assertThat(propriedadeContratadaList).hasSize(databaseSizeBeforeCreate + 1);
        PropriedadeContratada testPropriedadeContratada = propriedadeContratadaList.get(propriedadeContratadaList.size() - 1);
        assertThat(testPropriedadeContratada.getDataInicial()).isEqualTo(DEFAULT_DATA_INICIAL);
        assertThat(testPropriedadeContratada.getDataFinal()).isEqualTo(DEFAULT_DATA_FINAL);
        assertThat(testPropriedadeContratada.getQuantidadeCabecas()).isEqualTo(DEFAULT_QUANTIDADE_CABECAS);
        assertThat(testPropriedadeContratada.getValorContratado()).isEqualTo(DEFAULT_VALOR_CONTRATADO);
        assertThat(testPropriedadeContratada.getFormaPagamento()).isEqualTo(DEFAULT_FORMA_PAGAMENTO);
        assertThat(testPropriedadeContratada.getParcelas()).isEqualTo(DEFAULT_PARCELAS);
        assertThat(testPropriedadeContratada.getValorParcela()).isEqualTo(DEFAULT_VALOR_PARCELA);
    }

    @Test
    @Transactional
    public void createPropriedadeContratadaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = propriedadeContratadaRepository.findAll().size();

        // Create the PropriedadeContratada with an existing ID
        propriedadeContratada.setId(1L);
        PropriedadeContratadaDTO propriedadeContratadaDTO = propriedadeContratadaMapper.toDto(propriedadeContratada);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPropriedadeContratadaMockMvc.perform(post("/api/propriedade-contratadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propriedadeContratadaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PropriedadeContratada in the database
        List<PropriedadeContratada> propriedadeContratadaList = propriedadeContratadaRepository.findAll();
        assertThat(propriedadeContratadaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDataInicialIsRequired() throws Exception {
        int databaseSizeBeforeTest = propriedadeContratadaRepository.findAll().size();
        // set the field null
        propriedadeContratada.setDataInicial(null);

        // Create the PropriedadeContratada, which fails.
        PropriedadeContratadaDTO propriedadeContratadaDTO = propriedadeContratadaMapper.toDto(propriedadeContratada);

        restPropriedadeContratadaMockMvc.perform(post("/api/propriedade-contratadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propriedadeContratadaDTO)))
            .andExpect(status().isBadRequest());

        List<PropriedadeContratada> propriedadeContratadaList = propriedadeContratadaRepository.findAll();
        assertThat(propriedadeContratadaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataFinalIsRequired() throws Exception {
        int databaseSizeBeforeTest = propriedadeContratadaRepository.findAll().size();
        // set the field null
        propriedadeContratada.setDataFinal(null);

        // Create the PropriedadeContratada, which fails.
        PropriedadeContratadaDTO propriedadeContratadaDTO = propriedadeContratadaMapper.toDto(propriedadeContratada);

        restPropriedadeContratadaMockMvc.perform(post("/api/propriedade-contratadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propriedadeContratadaDTO)))
            .andExpect(status().isBadRequest());

        List<PropriedadeContratada> propriedadeContratadaList = propriedadeContratadaRepository.findAll();
        assertThat(propriedadeContratadaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantidadeCabecasIsRequired() throws Exception {
        int databaseSizeBeforeTest = propriedadeContratadaRepository.findAll().size();
        // set the field null
        propriedadeContratada.setQuantidadeCabecas(null);

        // Create the PropriedadeContratada, which fails.
        PropriedadeContratadaDTO propriedadeContratadaDTO = propriedadeContratadaMapper.toDto(propriedadeContratada);

        restPropriedadeContratadaMockMvc.perform(post("/api/propriedade-contratadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propriedadeContratadaDTO)))
            .andExpect(status().isBadRequest());

        List<PropriedadeContratada> propriedadeContratadaList = propriedadeContratadaRepository.findAll();
        assertThat(propriedadeContratadaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorContratadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = propriedadeContratadaRepository.findAll().size();
        // set the field null
        propriedadeContratada.setValorContratado(null);

        // Create the PropriedadeContratada, which fails.
        PropriedadeContratadaDTO propriedadeContratadaDTO = propriedadeContratadaMapper.toDto(propriedadeContratada);

        restPropriedadeContratadaMockMvc.perform(post("/api/propriedade-contratadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propriedadeContratadaDTO)))
            .andExpect(status().isBadRequest());

        List<PropriedadeContratada> propriedadeContratadaList = propriedadeContratadaRepository.findAll();
        assertThat(propriedadeContratadaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFormaPagamentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = propriedadeContratadaRepository.findAll().size();
        // set the field null
        propriedadeContratada.setFormaPagamento(null);

        // Create the PropriedadeContratada, which fails.
        PropriedadeContratadaDTO propriedadeContratadaDTO = propriedadeContratadaMapper.toDto(propriedadeContratada);

        restPropriedadeContratadaMockMvc.perform(post("/api/propriedade-contratadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propriedadeContratadaDTO)))
            .andExpect(status().isBadRequest());

        List<PropriedadeContratada> propriedadeContratadaList = propriedadeContratadaRepository.findAll();
        assertThat(propriedadeContratadaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkParcelasIsRequired() throws Exception {
        int databaseSizeBeforeTest = propriedadeContratadaRepository.findAll().size();
        // set the field null
        propriedadeContratada.setParcelas(null);

        // Create the PropriedadeContratada, which fails.
        PropriedadeContratadaDTO propriedadeContratadaDTO = propriedadeContratadaMapper.toDto(propriedadeContratada);

        restPropriedadeContratadaMockMvc.perform(post("/api/propriedade-contratadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propriedadeContratadaDTO)))
            .andExpect(status().isBadRequest());

        List<PropriedadeContratada> propriedadeContratadaList = propriedadeContratadaRepository.findAll();
        assertThat(propriedadeContratadaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorParcelaIsRequired() throws Exception {
        int databaseSizeBeforeTest = propriedadeContratadaRepository.findAll().size();
        // set the field null
        propriedadeContratada.setValorParcela(null);

        // Create the PropriedadeContratada, which fails.
        PropriedadeContratadaDTO propriedadeContratadaDTO = propriedadeContratadaMapper.toDto(propriedadeContratada);

        restPropriedadeContratadaMockMvc.perform(post("/api/propriedade-contratadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propriedadeContratadaDTO)))
            .andExpect(status().isBadRequest());

        List<PropriedadeContratada> propriedadeContratadaList = propriedadeContratadaRepository.findAll();
        assertThat(propriedadeContratadaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPropriedadeContratadas() throws Exception {
        // Initialize the database
        propriedadeContratadaRepository.saveAndFlush(propriedadeContratada);

        // Get all the propriedadeContratadaList
        restPropriedadeContratadaMockMvc.perform(get("/api/propriedade-contratadas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(propriedadeContratada.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataInicial").value(hasItem(DEFAULT_DATA_INICIAL.toString())))
            .andExpect(jsonPath("$.[*].dataFinal").value(hasItem(DEFAULT_DATA_FINAL.toString())))
            .andExpect(jsonPath("$.[*].quantidadeCabecas").value(hasItem(DEFAULT_QUANTIDADE_CABECAS)))
            .andExpect(jsonPath("$.[*].valorContratado").value(hasItem(DEFAULT_VALOR_CONTRATADO.intValue())))
            .andExpect(jsonPath("$.[*].formaPagamento").value(hasItem(DEFAULT_FORMA_PAGAMENTO.toString())))
            .andExpect(jsonPath("$.[*].parcelas").value(hasItem(DEFAULT_PARCELAS)))
            .andExpect(jsonPath("$.[*].valorParcela").value(hasItem(DEFAULT_VALOR_PARCELA.intValue())));
    }
    
    @Test
    @Transactional
    public void getPropriedadeContratada() throws Exception {
        // Initialize the database
        propriedadeContratadaRepository.saveAndFlush(propriedadeContratada);

        // Get the propriedadeContratada
        restPropriedadeContratadaMockMvc.perform(get("/api/propriedade-contratadas/{id}", propriedadeContratada.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(propriedadeContratada.getId().intValue()))
            .andExpect(jsonPath("$.dataInicial").value(DEFAULT_DATA_INICIAL.toString()))
            .andExpect(jsonPath("$.dataFinal").value(DEFAULT_DATA_FINAL.toString()))
            .andExpect(jsonPath("$.quantidadeCabecas").value(DEFAULT_QUANTIDADE_CABECAS))
            .andExpect(jsonPath("$.valorContratado").value(DEFAULT_VALOR_CONTRATADO.intValue()))
            .andExpect(jsonPath("$.formaPagamento").value(DEFAULT_FORMA_PAGAMENTO.toString()))
            .andExpect(jsonPath("$.parcelas").value(DEFAULT_PARCELAS))
            .andExpect(jsonPath("$.valorParcela").value(DEFAULT_VALOR_PARCELA.intValue()));
    }

    @Test
    @Transactional
    public void getAllPropriedadeContratadasByDataInicialIsEqualToSomething() throws Exception {
        // Initialize the database
        propriedadeContratadaRepository.saveAndFlush(propriedadeContratada);

        // Get all the propriedadeContratadaList where dataInicial equals to DEFAULT_DATA_INICIAL
        defaultPropriedadeContratadaShouldBeFound("dataInicial.equals=" + DEFAULT_DATA_INICIAL);

        // Get all the propriedadeContratadaList where dataInicial equals to UPDATED_DATA_INICIAL
        defaultPropriedadeContratadaShouldNotBeFound("dataInicial.equals=" + UPDATED_DATA_INICIAL);
    }

    @Test
    @Transactional
    public void getAllPropriedadeContratadasByDataInicialIsInShouldWork() throws Exception {
        // Initialize the database
        propriedadeContratadaRepository.saveAndFlush(propriedadeContratada);

        // Get all the propriedadeContratadaList where dataInicial in DEFAULT_DATA_INICIAL or UPDATED_DATA_INICIAL
        defaultPropriedadeContratadaShouldBeFound("dataInicial.in=" + DEFAULT_DATA_INICIAL + "," + UPDATED_DATA_INICIAL);

        // Get all the propriedadeContratadaList where dataInicial equals to UPDATED_DATA_INICIAL
        defaultPropriedadeContratadaShouldNotBeFound("dataInicial.in=" + UPDATED_DATA_INICIAL);
    }

    @Test
    @Transactional
    public void getAllPropriedadeContratadasByDataInicialIsNullOrNotNull() throws Exception {
        // Initialize the database
        propriedadeContratadaRepository.saveAndFlush(propriedadeContratada);

        // Get all the propriedadeContratadaList where dataInicial is not null
        defaultPropriedadeContratadaShouldBeFound("dataInicial.specified=true");

        // Get all the propriedadeContratadaList where dataInicial is null
        defaultPropriedadeContratadaShouldNotBeFound("dataInicial.specified=false");
    }

    @Test
    @Transactional
    public void getAllPropriedadeContratadasByDataFinalIsEqualToSomething() throws Exception {
        // Initialize the database
        propriedadeContratadaRepository.saveAndFlush(propriedadeContratada);

        // Get all the propriedadeContratadaList where dataFinal equals to DEFAULT_DATA_FINAL
        defaultPropriedadeContratadaShouldBeFound("dataFinal.equals=" + DEFAULT_DATA_FINAL);

        // Get all the propriedadeContratadaList where dataFinal equals to UPDATED_DATA_FINAL
        defaultPropriedadeContratadaShouldNotBeFound("dataFinal.equals=" + UPDATED_DATA_FINAL);
    }

    @Test
    @Transactional
    public void getAllPropriedadeContratadasByDataFinalIsInShouldWork() throws Exception {
        // Initialize the database
        propriedadeContratadaRepository.saveAndFlush(propriedadeContratada);

        // Get all the propriedadeContratadaList where dataFinal in DEFAULT_DATA_FINAL or UPDATED_DATA_FINAL
        defaultPropriedadeContratadaShouldBeFound("dataFinal.in=" + DEFAULT_DATA_FINAL + "," + UPDATED_DATA_FINAL);

        // Get all the propriedadeContratadaList where dataFinal equals to UPDATED_DATA_FINAL
        defaultPropriedadeContratadaShouldNotBeFound("dataFinal.in=" + UPDATED_DATA_FINAL);
    }

    @Test
    @Transactional
    public void getAllPropriedadeContratadasByDataFinalIsNullOrNotNull() throws Exception {
        // Initialize the database
        propriedadeContratadaRepository.saveAndFlush(propriedadeContratada);

        // Get all the propriedadeContratadaList where dataFinal is not null
        defaultPropriedadeContratadaShouldBeFound("dataFinal.specified=true");

        // Get all the propriedadeContratadaList where dataFinal is null
        defaultPropriedadeContratadaShouldNotBeFound("dataFinal.specified=false");
    }

    @Test
    @Transactional
    public void getAllPropriedadeContratadasByQuantidadeCabecasIsEqualToSomething() throws Exception {
        // Initialize the database
        propriedadeContratadaRepository.saveAndFlush(propriedadeContratada);

        // Get all the propriedadeContratadaList where quantidadeCabecas equals to DEFAULT_QUANTIDADE_CABECAS
        defaultPropriedadeContratadaShouldBeFound("quantidadeCabecas.equals=" + DEFAULT_QUANTIDADE_CABECAS);

        // Get all the propriedadeContratadaList where quantidadeCabecas equals to UPDATED_QUANTIDADE_CABECAS
        defaultPropriedadeContratadaShouldNotBeFound("quantidadeCabecas.equals=" + UPDATED_QUANTIDADE_CABECAS);
    }

    @Test
    @Transactional
    public void getAllPropriedadeContratadasByQuantidadeCabecasIsInShouldWork() throws Exception {
        // Initialize the database
        propriedadeContratadaRepository.saveAndFlush(propriedadeContratada);

        // Get all the propriedadeContratadaList where quantidadeCabecas in DEFAULT_QUANTIDADE_CABECAS or UPDATED_QUANTIDADE_CABECAS
        defaultPropriedadeContratadaShouldBeFound("quantidadeCabecas.in=" + DEFAULT_QUANTIDADE_CABECAS + "," + UPDATED_QUANTIDADE_CABECAS);

        // Get all the propriedadeContratadaList where quantidadeCabecas equals to UPDATED_QUANTIDADE_CABECAS
        defaultPropriedadeContratadaShouldNotBeFound("quantidadeCabecas.in=" + UPDATED_QUANTIDADE_CABECAS);
    }

    @Test
    @Transactional
    public void getAllPropriedadeContratadasByQuantidadeCabecasIsNullOrNotNull() throws Exception {
        // Initialize the database
        propriedadeContratadaRepository.saveAndFlush(propriedadeContratada);

        // Get all the propriedadeContratadaList where quantidadeCabecas is not null
        defaultPropriedadeContratadaShouldBeFound("quantidadeCabecas.specified=true");

        // Get all the propriedadeContratadaList where quantidadeCabecas is null
        defaultPropriedadeContratadaShouldNotBeFound("quantidadeCabecas.specified=false");
    }

    @Test
    @Transactional
    public void getAllPropriedadeContratadasByQuantidadeCabecasIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        propriedadeContratadaRepository.saveAndFlush(propriedadeContratada);

        // Get all the propriedadeContratadaList where quantidadeCabecas greater than or equals to DEFAULT_QUANTIDADE_CABECAS
        defaultPropriedadeContratadaShouldBeFound("quantidadeCabecas.greaterOrEqualThan=" + DEFAULT_QUANTIDADE_CABECAS);

        // Get all the propriedadeContratadaList where quantidadeCabecas greater than or equals to UPDATED_QUANTIDADE_CABECAS
        defaultPropriedadeContratadaShouldNotBeFound("quantidadeCabecas.greaterOrEqualThan=" + UPDATED_QUANTIDADE_CABECAS);
    }

    @Test
    @Transactional
    public void getAllPropriedadeContratadasByQuantidadeCabecasIsLessThanSomething() throws Exception {
        // Initialize the database
        propriedadeContratadaRepository.saveAndFlush(propriedadeContratada);

        // Get all the propriedadeContratadaList where quantidadeCabecas less than or equals to DEFAULT_QUANTIDADE_CABECAS
        defaultPropriedadeContratadaShouldNotBeFound("quantidadeCabecas.lessThan=" + DEFAULT_QUANTIDADE_CABECAS);

        // Get all the propriedadeContratadaList where quantidadeCabecas less than or equals to UPDATED_QUANTIDADE_CABECAS
        defaultPropriedadeContratadaShouldBeFound("quantidadeCabecas.lessThan=" + UPDATED_QUANTIDADE_CABECAS);
    }


    @Test
    @Transactional
    public void getAllPropriedadeContratadasByValorContratadoIsEqualToSomething() throws Exception {
        // Initialize the database
        propriedadeContratadaRepository.saveAndFlush(propriedadeContratada);

        // Get all the propriedadeContratadaList where valorContratado equals to DEFAULT_VALOR_CONTRATADO
        defaultPropriedadeContratadaShouldBeFound("valorContratado.equals=" + DEFAULT_VALOR_CONTRATADO);

        // Get all the propriedadeContratadaList where valorContratado equals to UPDATED_VALOR_CONTRATADO
        defaultPropriedadeContratadaShouldNotBeFound("valorContratado.equals=" + UPDATED_VALOR_CONTRATADO);
    }

    @Test
    @Transactional
    public void getAllPropriedadeContratadasByValorContratadoIsInShouldWork() throws Exception {
        // Initialize the database
        propriedadeContratadaRepository.saveAndFlush(propriedadeContratada);

        // Get all the propriedadeContratadaList where valorContratado in DEFAULT_VALOR_CONTRATADO or UPDATED_VALOR_CONTRATADO
        defaultPropriedadeContratadaShouldBeFound("valorContratado.in=" + DEFAULT_VALOR_CONTRATADO + "," + UPDATED_VALOR_CONTRATADO);

        // Get all the propriedadeContratadaList where valorContratado equals to UPDATED_VALOR_CONTRATADO
        defaultPropriedadeContratadaShouldNotBeFound("valorContratado.in=" + UPDATED_VALOR_CONTRATADO);
    }

    @Test
    @Transactional
    public void getAllPropriedadeContratadasByValorContratadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        propriedadeContratadaRepository.saveAndFlush(propriedadeContratada);

        // Get all the propriedadeContratadaList where valorContratado is not null
        defaultPropriedadeContratadaShouldBeFound("valorContratado.specified=true");

        // Get all the propriedadeContratadaList where valorContratado is null
        defaultPropriedadeContratadaShouldNotBeFound("valorContratado.specified=false");
    }

    @Test
    @Transactional
    public void getAllPropriedadeContratadasByFormaPagamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        propriedadeContratadaRepository.saveAndFlush(propriedadeContratada);

        // Get all the propriedadeContratadaList where formaPagamento equals to DEFAULT_FORMA_PAGAMENTO
        defaultPropriedadeContratadaShouldBeFound("formaPagamento.equals=" + DEFAULT_FORMA_PAGAMENTO);

        // Get all the propriedadeContratadaList where formaPagamento equals to UPDATED_FORMA_PAGAMENTO
        defaultPropriedadeContratadaShouldNotBeFound("formaPagamento.equals=" + UPDATED_FORMA_PAGAMENTO);
    }

    @Test
    @Transactional
    public void getAllPropriedadeContratadasByFormaPagamentoIsInShouldWork() throws Exception {
        // Initialize the database
        propriedadeContratadaRepository.saveAndFlush(propriedadeContratada);

        // Get all the propriedadeContratadaList where formaPagamento in DEFAULT_FORMA_PAGAMENTO or UPDATED_FORMA_PAGAMENTO
        defaultPropriedadeContratadaShouldBeFound("formaPagamento.in=" + DEFAULT_FORMA_PAGAMENTO + "," + UPDATED_FORMA_PAGAMENTO);

        // Get all the propriedadeContratadaList where formaPagamento equals to UPDATED_FORMA_PAGAMENTO
        defaultPropriedadeContratadaShouldNotBeFound("formaPagamento.in=" + UPDATED_FORMA_PAGAMENTO);
    }

    @Test
    @Transactional
    public void getAllPropriedadeContratadasByFormaPagamentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        propriedadeContratadaRepository.saveAndFlush(propriedadeContratada);

        // Get all the propriedadeContratadaList where formaPagamento is not null
        defaultPropriedadeContratadaShouldBeFound("formaPagamento.specified=true");

        // Get all the propriedadeContratadaList where formaPagamento is null
        defaultPropriedadeContratadaShouldNotBeFound("formaPagamento.specified=false");
    }

    @Test
    @Transactional
    public void getAllPropriedadeContratadasByParcelasIsEqualToSomething() throws Exception {
        // Initialize the database
        propriedadeContratadaRepository.saveAndFlush(propriedadeContratada);

        // Get all the propriedadeContratadaList where parcelas equals to DEFAULT_PARCELAS
        defaultPropriedadeContratadaShouldBeFound("parcelas.equals=" + DEFAULT_PARCELAS);

        // Get all the propriedadeContratadaList where parcelas equals to UPDATED_PARCELAS
        defaultPropriedadeContratadaShouldNotBeFound("parcelas.equals=" + UPDATED_PARCELAS);
    }

    @Test
    @Transactional
    public void getAllPropriedadeContratadasByParcelasIsInShouldWork() throws Exception {
        // Initialize the database
        propriedadeContratadaRepository.saveAndFlush(propriedadeContratada);

        // Get all the propriedadeContratadaList where parcelas in DEFAULT_PARCELAS or UPDATED_PARCELAS
        defaultPropriedadeContratadaShouldBeFound("parcelas.in=" + DEFAULT_PARCELAS + "," + UPDATED_PARCELAS);

        // Get all the propriedadeContratadaList where parcelas equals to UPDATED_PARCELAS
        defaultPropriedadeContratadaShouldNotBeFound("parcelas.in=" + UPDATED_PARCELAS);
    }

    @Test
    @Transactional
    public void getAllPropriedadeContratadasByParcelasIsNullOrNotNull() throws Exception {
        // Initialize the database
        propriedadeContratadaRepository.saveAndFlush(propriedadeContratada);

        // Get all the propriedadeContratadaList where parcelas is not null
        defaultPropriedadeContratadaShouldBeFound("parcelas.specified=true");

        // Get all the propriedadeContratadaList where parcelas is null
        defaultPropriedadeContratadaShouldNotBeFound("parcelas.specified=false");
    }

    @Test
    @Transactional
    public void getAllPropriedadeContratadasByParcelasIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        propriedadeContratadaRepository.saveAndFlush(propriedadeContratada);

        // Get all the propriedadeContratadaList where parcelas greater than or equals to DEFAULT_PARCELAS
        defaultPropriedadeContratadaShouldBeFound("parcelas.greaterOrEqualThan=" + DEFAULT_PARCELAS);

        // Get all the propriedadeContratadaList where parcelas greater than or equals to UPDATED_PARCELAS
        defaultPropriedadeContratadaShouldNotBeFound("parcelas.greaterOrEqualThan=" + UPDATED_PARCELAS);
    }

    @Test
    @Transactional
    public void getAllPropriedadeContratadasByParcelasIsLessThanSomething() throws Exception {
        // Initialize the database
        propriedadeContratadaRepository.saveAndFlush(propriedadeContratada);

        // Get all the propriedadeContratadaList where parcelas less than or equals to DEFAULT_PARCELAS
        defaultPropriedadeContratadaShouldNotBeFound("parcelas.lessThan=" + DEFAULT_PARCELAS);

        // Get all the propriedadeContratadaList where parcelas less than or equals to UPDATED_PARCELAS
        defaultPropriedadeContratadaShouldBeFound("parcelas.lessThan=" + UPDATED_PARCELAS);
    }


    @Test
    @Transactional
    public void getAllPropriedadeContratadasByValorParcelaIsEqualToSomething() throws Exception {
        // Initialize the database
        propriedadeContratadaRepository.saveAndFlush(propriedadeContratada);

        // Get all the propriedadeContratadaList where valorParcela equals to DEFAULT_VALOR_PARCELA
        defaultPropriedadeContratadaShouldBeFound("valorParcela.equals=" + DEFAULT_VALOR_PARCELA);

        // Get all the propriedadeContratadaList where valorParcela equals to UPDATED_VALOR_PARCELA
        defaultPropriedadeContratadaShouldNotBeFound("valorParcela.equals=" + UPDATED_VALOR_PARCELA);
    }

    @Test
    @Transactional
    public void getAllPropriedadeContratadasByValorParcelaIsInShouldWork() throws Exception {
        // Initialize the database
        propriedadeContratadaRepository.saveAndFlush(propriedadeContratada);

        // Get all the propriedadeContratadaList where valorParcela in DEFAULT_VALOR_PARCELA or UPDATED_VALOR_PARCELA
        defaultPropriedadeContratadaShouldBeFound("valorParcela.in=" + DEFAULT_VALOR_PARCELA + "," + UPDATED_VALOR_PARCELA);

        // Get all the propriedadeContratadaList where valorParcela equals to UPDATED_VALOR_PARCELA
        defaultPropriedadeContratadaShouldNotBeFound("valorParcela.in=" + UPDATED_VALOR_PARCELA);
    }

    @Test
    @Transactional
    public void getAllPropriedadeContratadasByValorParcelaIsNullOrNotNull() throws Exception {
        // Initialize the database
        propriedadeContratadaRepository.saveAndFlush(propriedadeContratada);

        // Get all the propriedadeContratadaList where valorParcela is not null
        defaultPropriedadeContratadaShouldBeFound("valorParcela.specified=true");

        // Get all the propriedadeContratadaList where valorParcela is null
        defaultPropriedadeContratadaShouldNotBeFound("valorParcela.specified=false");
    }

    @Test
    @Transactional
    public void getAllPropriedadeContratadasByPropriedadeIsEqualToSomething() throws Exception {
        // Get already existing entity
        Propriedade propriedade = propriedadeContratada.getPropriedade();
        propriedadeContratadaRepository.saveAndFlush(propriedadeContratada);
        Long propriedadeId = propriedade.getId();

        // Get all the propriedadeContratadaList where propriedade equals to propriedadeId
        defaultPropriedadeContratadaShouldBeFound("propriedadeId.equals=" + propriedadeId);

        // Get all the propriedadeContratadaList where propriedade equals to propriedadeId + 1
        defaultPropriedadeContratadaShouldNotBeFound("propriedadeId.equals=" + (propriedadeId + 1));
    }


    @Test
    @Transactional
    public void getAllPropriedadeContratadasByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = propriedadeContratada.getUser();
        propriedadeContratadaRepository.saveAndFlush(propriedadeContratada);
        Long userId = user.getId();

        // Get all the propriedadeContratadaList where user equals to userId
        defaultPropriedadeContratadaShouldBeFound("userId.equals=" + userId);

        // Get all the propriedadeContratadaList where user equals to userId + 1
        defaultPropriedadeContratadaShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPropriedadeContratadaShouldBeFound(String filter) throws Exception {
        restPropriedadeContratadaMockMvc.perform(get("/api/propriedade-contratadas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(propriedadeContratada.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataInicial").value(hasItem(DEFAULT_DATA_INICIAL.toString())))
            .andExpect(jsonPath("$.[*].dataFinal").value(hasItem(DEFAULT_DATA_FINAL.toString())))
            .andExpect(jsonPath("$.[*].quantidadeCabecas").value(hasItem(DEFAULT_QUANTIDADE_CABECAS)))
            .andExpect(jsonPath("$.[*].valorContratado").value(hasItem(DEFAULT_VALOR_CONTRATADO.intValue())))
            .andExpect(jsonPath("$.[*].formaPagamento").value(hasItem(DEFAULT_FORMA_PAGAMENTO.toString())))
            .andExpect(jsonPath("$.[*].parcelas").value(hasItem(DEFAULT_PARCELAS)))
            .andExpect(jsonPath("$.[*].valorParcela").value(hasItem(DEFAULT_VALOR_PARCELA.intValue())));

        // Check, that the count call also returns 1
        restPropriedadeContratadaMockMvc.perform(get("/api/propriedade-contratadas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPropriedadeContratadaShouldNotBeFound(String filter) throws Exception {
        restPropriedadeContratadaMockMvc.perform(get("/api/propriedade-contratadas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPropriedadeContratadaMockMvc.perform(get("/api/propriedade-contratadas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPropriedadeContratada() throws Exception {
        // Get the propriedadeContratada
        restPropriedadeContratadaMockMvc.perform(get("/api/propriedade-contratadas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePropriedadeContratada() throws Exception {
        // Initialize the database
        propriedadeContratadaRepository.saveAndFlush(propriedadeContratada);

        int databaseSizeBeforeUpdate = propriedadeContratadaRepository.findAll().size();

        // Update the propriedadeContratada
        PropriedadeContratada updatedPropriedadeContratada = propriedadeContratadaRepository.findById(propriedadeContratada.getId()).get();
        // Disconnect from session so that the updates on updatedPropriedadeContratada are not directly saved in db
        em.detach(updatedPropriedadeContratada);
        updatedPropriedadeContratada
            .dataInicial(UPDATED_DATA_INICIAL)
            .dataFinal(UPDATED_DATA_FINAL)
            .quantidadeCabecas(UPDATED_QUANTIDADE_CABECAS)
            .valorContratado(UPDATED_VALOR_CONTRATADO)
            .formaPagamento(UPDATED_FORMA_PAGAMENTO)
            .parcelas(UPDATED_PARCELAS)
            .valorParcela(UPDATED_VALOR_PARCELA);
        PropriedadeContratadaDTO propriedadeContratadaDTO = propriedadeContratadaMapper.toDto(updatedPropriedadeContratada);

        restPropriedadeContratadaMockMvc.perform(put("/api/propriedade-contratadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propriedadeContratadaDTO)))
            .andExpect(status().isOk());

        // Validate the PropriedadeContratada in the database
        List<PropriedadeContratada> propriedadeContratadaList = propriedadeContratadaRepository.findAll();
        assertThat(propriedadeContratadaList).hasSize(databaseSizeBeforeUpdate);
        PropriedadeContratada testPropriedadeContratada = propriedadeContratadaList.get(propriedadeContratadaList.size() - 1);
        assertThat(testPropriedadeContratada.getDataInicial()).isEqualTo(UPDATED_DATA_INICIAL);
        assertThat(testPropriedadeContratada.getDataFinal()).isEqualTo(UPDATED_DATA_FINAL);
        assertThat(testPropriedadeContratada.getQuantidadeCabecas()).isEqualTo(UPDATED_QUANTIDADE_CABECAS);
        assertThat(testPropriedadeContratada.getValorContratado()).isEqualTo(UPDATED_VALOR_CONTRATADO);
        assertThat(testPropriedadeContratada.getFormaPagamento()).isEqualTo(UPDATED_FORMA_PAGAMENTO);
        assertThat(testPropriedadeContratada.getParcelas()).isEqualTo(UPDATED_PARCELAS);
        assertThat(testPropriedadeContratada.getValorParcela()).isEqualTo(UPDATED_VALOR_PARCELA);
    }

    @Test
    @Transactional
    public void updateNonExistingPropriedadeContratada() throws Exception {
        int databaseSizeBeforeUpdate = propriedadeContratadaRepository.findAll().size();

        // Create the PropriedadeContratada
        PropriedadeContratadaDTO propriedadeContratadaDTO = propriedadeContratadaMapper.toDto(propriedadeContratada);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPropriedadeContratadaMockMvc.perform(put("/api/propriedade-contratadas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propriedadeContratadaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PropriedadeContratada in the database
        List<PropriedadeContratada> propriedadeContratadaList = propriedadeContratadaRepository.findAll();
        assertThat(propriedadeContratadaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePropriedadeContratada() throws Exception {
        // Initialize the database
        propriedadeContratadaRepository.saveAndFlush(propriedadeContratada);

        int databaseSizeBeforeDelete = propriedadeContratadaRepository.findAll().size();

        // Delete the propriedadeContratada
        restPropriedadeContratadaMockMvc.perform(delete("/api/propriedade-contratadas/{id}", propriedadeContratada.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<PropriedadeContratada> propriedadeContratadaList = propriedadeContratadaRepository.findAll();
        assertThat(propriedadeContratadaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PropriedadeContratada.class);
        PropriedadeContratada propriedadeContratada1 = new PropriedadeContratada();
        propriedadeContratada1.setId(1L);
        PropriedadeContratada propriedadeContratada2 = new PropriedadeContratada();
        propriedadeContratada2.setId(propriedadeContratada1.getId());
        assertThat(propriedadeContratada1).isEqualTo(propriedadeContratada2);
        propriedadeContratada2.setId(2L);
        assertThat(propriedadeContratada1).isNotEqualTo(propriedadeContratada2);
        propriedadeContratada1.setId(null);
        assertThat(propriedadeContratada1).isNotEqualTo(propriedadeContratada2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PropriedadeContratadaDTO.class);
        PropriedadeContratadaDTO propriedadeContratadaDTO1 = new PropriedadeContratadaDTO();
        propriedadeContratadaDTO1.setId(1L);
        PropriedadeContratadaDTO propriedadeContratadaDTO2 = new PropriedadeContratadaDTO();
        assertThat(propriedadeContratadaDTO1).isNotEqualTo(propriedadeContratadaDTO2);
        propriedadeContratadaDTO2.setId(propriedadeContratadaDTO1.getId());
        assertThat(propriedadeContratadaDTO1).isEqualTo(propriedadeContratadaDTO2);
        propriedadeContratadaDTO2.setId(2L);
        assertThat(propriedadeContratadaDTO1).isNotEqualTo(propriedadeContratadaDTO2);
        propriedadeContratadaDTO1.setId(null);
        assertThat(propriedadeContratadaDTO1).isNotEqualTo(propriedadeContratadaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(propriedadeContratadaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(propriedadeContratadaMapper.fromId(null)).isNull();
    }
}

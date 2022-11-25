package com.impulse.laerson.myapprequisito.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.impulse.laerson.myapprequisito.IntegrationTest;
import com.impulse.laerson.myapprequisito.domain.RecursoFuncionalidade;
import com.impulse.laerson.myapprequisito.domain.enumeration.Complexibilidade;
import com.impulse.laerson.myapprequisito.domain.enumeration.Prioridade;
import com.impulse.laerson.myapprequisito.domain.enumeration.Status;
import com.impulse.laerson.myapprequisito.repository.RecursoFuncionalidadeRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RecursoFuncionalidadeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class RecursoFuncionalidadeResourceIT {

    private static final String DEFAULT_IDRF = "AAAAAAAAAA";
    private static final String UPDATED_IDRF = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO_REQUISITO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO_REQUISITO = "BBBBBBBBBB";

    private static final Prioridade DEFAULT_PRIORIDADE = Prioridade.BAIXA;
    private static final Prioridade UPDATED_PRIORIDADE = Prioridade.MEDIA;

    private static final Complexibilidade DEFAULT_COMPLEXIBILIDADE = Complexibilidade.BAIXA;
    private static final Complexibilidade UPDATED_COMPLEXIBILIDADE = Complexibilidade.MEDIA;

    private static final String DEFAULT_ESFORCO = "AAAAAAAAAA";
    private static final String UPDATED_ESFORCO = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.AGUARDANDO;
    private static final Status UPDATED_STATUS = Status.PRIORIDADE;

    private static final String ENTITY_API_URL = "/api/recurso-funcionalidades";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RecursoFuncionalidadeRepository recursoFuncionalidadeRepository;

    @Mock
    private RecursoFuncionalidadeRepository recursoFuncionalidadeRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecursoFuncionalidadeMockMvc;

    private RecursoFuncionalidade recursoFuncionalidade;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecursoFuncionalidade createEntity(EntityManager em) {
        RecursoFuncionalidade recursoFuncionalidade = new RecursoFuncionalidade()
            .idrf(DEFAULT_IDRF)
            .descricaoRequisito(DEFAULT_DESCRICAO_REQUISITO)
            .prioridade(DEFAULT_PRIORIDADE)
            .complexibilidade(DEFAULT_COMPLEXIBILIDADE)
            .esforco(DEFAULT_ESFORCO)
            .status(DEFAULT_STATUS);
        return recursoFuncionalidade;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecursoFuncionalidade createUpdatedEntity(EntityManager em) {
        RecursoFuncionalidade recursoFuncionalidade = new RecursoFuncionalidade()
            .idrf(UPDATED_IDRF)
            .descricaoRequisito(UPDATED_DESCRICAO_REQUISITO)
            .prioridade(UPDATED_PRIORIDADE)
            .complexibilidade(UPDATED_COMPLEXIBILIDADE)
            .esforco(UPDATED_ESFORCO)
            .status(UPDATED_STATUS);
        return recursoFuncionalidade;
    }

    @BeforeEach
    public void initTest() {
        recursoFuncionalidade = createEntity(em);
    }

    @Test
    @Transactional
    void createRecursoFuncionalidade() throws Exception {
        int databaseSizeBeforeCreate = recursoFuncionalidadeRepository.findAll().size();
        // Create the RecursoFuncionalidade
        restRecursoFuncionalidadeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recursoFuncionalidade))
            )
            .andExpect(status().isCreated());

        // Validate the RecursoFuncionalidade in the database
        List<RecursoFuncionalidade> recursoFuncionalidadeList = recursoFuncionalidadeRepository.findAll();
        assertThat(recursoFuncionalidadeList).hasSize(databaseSizeBeforeCreate + 1);
        RecursoFuncionalidade testRecursoFuncionalidade = recursoFuncionalidadeList.get(recursoFuncionalidadeList.size() - 1);
        assertThat(testRecursoFuncionalidade.getIdrf()).isEqualTo(DEFAULT_IDRF);
        assertThat(testRecursoFuncionalidade.getDescricaoRequisito()).isEqualTo(DEFAULT_DESCRICAO_REQUISITO);
        assertThat(testRecursoFuncionalidade.getPrioridade()).isEqualTo(DEFAULT_PRIORIDADE);
        assertThat(testRecursoFuncionalidade.getComplexibilidade()).isEqualTo(DEFAULT_COMPLEXIBILIDADE);
        assertThat(testRecursoFuncionalidade.getEsforco()).isEqualTo(DEFAULT_ESFORCO);
        assertThat(testRecursoFuncionalidade.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createRecursoFuncionalidadeWithExistingId() throws Exception {
        // Create the RecursoFuncionalidade with an existing ID
        recursoFuncionalidade.setId(1L);

        int databaseSizeBeforeCreate = recursoFuncionalidadeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecursoFuncionalidadeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recursoFuncionalidade))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecursoFuncionalidade in the database
        List<RecursoFuncionalidade> recursoFuncionalidadeList = recursoFuncionalidadeRepository.findAll();
        assertThat(recursoFuncionalidadeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdrfIsRequired() throws Exception {
        int databaseSizeBeforeTest = recursoFuncionalidadeRepository.findAll().size();
        // set the field null
        recursoFuncionalidade.setIdrf(null);

        // Create the RecursoFuncionalidade, which fails.

        restRecursoFuncionalidadeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recursoFuncionalidade))
            )
            .andExpect(status().isBadRequest());

        List<RecursoFuncionalidade> recursoFuncionalidadeList = recursoFuncionalidadeRepository.findAll();
        assertThat(recursoFuncionalidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescricaoRequisitoIsRequired() throws Exception {
        int databaseSizeBeforeTest = recursoFuncionalidadeRepository.findAll().size();
        // set the field null
        recursoFuncionalidade.setDescricaoRequisito(null);

        // Create the RecursoFuncionalidade, which fails.

        restRecursoFuncionalidadeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recursoFuncionalidade))
            )
            .andExpect(status().isBadRequest());

        List<RecursoFuncionalidade> recursoFuncionalidadeList = recursoFuncionalidadeRepository.findAll();
        assertThat(recursoFuncionalidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrioridadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = recursoFuncionalidadeRepository.findAll().size();
        // set the field null
        recursoFuncionalidade.setPrioridade(null);

        // Create the RecursoFuncionalidade, which fails.

        restRecursoFuncionalidadeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recursoFuncionalidade))
            )
            .andExpect(status().isBadRequest());

        List<RecursoFuncionalidade> recursoFuncionalidadeList = recursoFuncionalidadeRepository.findAll();
        assertThat(recursoFuncionalidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkComplexibilidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = recursoFuncionalidadeRepository.findAll().size();
        // set the field null
        recursoFuncionalidade.setComplexibilidade(null);

        // Create the RecursoFuncionalidade, which fails.

        restRecursoFuncionalidadeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recursoFuncionalidade))
            )
            .andExpect(status().isBadRequest());

        List<RecursoFuncionalidade> recursoFuncionalidadeList = recursoFuncionalidadeRepository.findAll();
        assertThat(recursoFuncionalidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = recursoFuncionalidadeRepository.findAll().size();
        // set the field null
        recursoFuncionalidade.setStatus(null);

        // Create the RecursoFuncionalidade, which fails.

        restRecursoFuncionalidadeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recursoFuncionalidade))
            )
            .andExpect(status().isBadRequest());

        List<RecursoFuncionalidade> recursoFuncionalidadeList = recursoFuncionalidadeRepository.findAll();
        assertThat(recursoFuncionalidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRecursoFuncionalidades() throws Exception {
        // Initialize the database
        recursoFuncionalidadeRepository.saveAndFlush(recursoFuncionalidade);

        // Get all the recursoFuncionalidadeList
        restRecursoFuncionalidadeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recursoFuncionalidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].idrf").value(hasItem(DEFAULT_IDRF)))
            .andExpect(jsonPath("$.[*].descricaoRequisito").value(hasItem(DEFAULT_DESCRICAO_REQUISITO)))
            .andExpect(jsonPath("$.[*].prioridade").value(hasItem(DEFAULT_PRIORIDADE.toString())))
            .andExpect(jsonPath("$.[*].complexibilidade").value(hasItem(DEFAULT_COMPLEXIBILIDADE.toString())))
            .andExpect(jsonPath("$.[*].esforco").value(hasItem(DEFAULT_ESFORCO)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRecursoFuncionalidadesWithEagerRelationshipsIsEnabled() throws Exception {
        when(recursoFuncionalidadeRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRecursoFuncionalidadeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(recursoFuncionalidadeRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRecursoFuncionalidadesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(recursoFuncionalidadeRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRecursoFuncionalidadeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(recursoFuncionalidadeRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getRecursoFuncionalidade() throws Exception {
        // Initialize the database
        recursoFuncionalidadeRepository.saveAndFlush(recursoFuncionalidade);

        // Get the recursoFuncionalidade
        restRecursoFuncionalidadeMockMvc
            .perform(get(ENTITY_API_URL_ID, recursoFuncionalidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recursoFuncionalidade.getId().intValue()))
            .andExpect(jsonPath("$.idrf").value(DEFAULT_IDRF))
            .andExpect(jsonPath("$.descricaoRequisito").value(DEFAULT_DESCRICAO_REQUISITO))
            .andExpect(jsonPath("$.prioridade").value(DEFAULT_PRIORIDADE.toString()))
            .andExpect(jsonPath("$.complexibilidade").value(DEFAULT_COMPLEXIBILIDADE.toString()))
            .andExpect(jsonPath("$.esforco").value(DEFAULT_ESFORCO))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingRecursoFuncionalidade() throws Exception {
        // Get the recursoFuncionalidade
        restRecursoFuncionalidadeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRecursoFuncionalidade() throws Exception {
        // Initialize the database
        recursoFuncionalidadeRepository.saveAndFlush(recursoFuncionalidade);

        int databaseSizeBeforeUpdate = recursoFuncionalidadeRepository.findAll().size();

        // Update the recursoFuncionalidade
        RecursoFuncionalidade updatedRecursoFuncionalidade = recursoFuncionalidadeRepository.findById(recursoFuncionalidade.getId()).get();
        // Disconnect from session so that the updates on updatedRecursoFuncionalidade are not directly saved in db
        em.detach(updatedRecursoFuncionalidade);
        updatedRecursoFuncionalidade
            .idrf(UPDATED_IDRF)
            .descricaoRequisito(UPDATED_DESCRICAO_REQUISITO)
            .prioridade(UPDATED_PRIORIDADE)
            .complexibilidade(UPDATED_COMPLEXIBILIDADE)
            .esforco(UPDATED_ESFORCO)
            .status(UPDATED_STATUS);

        restRecursoFuncionalidadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRecursoFuncionalidade.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRecursoFuncionalidade))
            )
            .andExpect(status().isOk());

        // Validate the RecursoFuncionalidade in the database
        List<RecursoFuncionalidade> recursoFuncionalidadeList = recursoFuncionalidadeRepository.findAll();
        assertThat(recursoFuncionalidadeList).hasSize(databaseSizeBeforeUpdate);
        RecursoFuncionalidade testRecursoFuncionalidade = recursoFuncionalidadeList.get(recursoFuncionalidadeList.size() - 1);
        assertThat(testRecursoFuncionalidade.getIdrf()).isEqualTo(UPDATED_IDRF);
        assertThat(testRecursoFuncionalidade.getDescricaoRequisito()).isEqualTo(UPDATED_DESCRICAO_REQUISITO);
        assertThat(testRecursoFuncionalidade.getPrioridade()).isEqualTo(UPDATED_PRIORIDADE);
        assertThat(testRecursoFuncionalidade.getComplexibilidade()).isEqualTo(UPDATED_COMPLEXIBILIDADE);
        assertThat(testRecursoFuncionalidade.getEsforco()).isEqualTo(UPDATED_ESFORCO);
        assertThat(testRecursoFuncionalidade.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingRecursoFuncionalidade() throws Exception {
        int databaseSizeBeforeUpdate = recursoFuncionalidadeRepository.findAll().size();
        recursoFuncionalidade.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecursoFuncionalidadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, recursoFuncionalidade.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recursoFuncionalidade))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecursoFuncionalidade in the database
        List<RecursoFuncionalidade> recursoFuncionalidadeList = recursoFuncionalidadeRepository.findAll();
        assertThat(recursoFuncionalidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRecursoFuncionalidade() throws Exception {
        int databaseSizeBeforeUpdate = recursoFuncionalidadeRepository.findAll().size();
        recursoFuncionalidade.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecursoFuncionalidadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recursoFuncionalidade))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecursoFuncionalidade in the database
        List<RecursoFuncionalidade> recursoFuncionalidadeList = recursoFuncionalidadeRepository.findAll();
        assertThat(recursoFuncionalidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRecursoFuncionalidade() throws Exception {
        int databaseSizeBeforeUpdate = recursoFuncionalidadeRepository.findAll().size();
        recursoFuncionalidade.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecursoFuncionalidadeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recursoFuncionalidade))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RecursoFuncionalidade in the database
        List<RecursoFuncionalidade> recursoFuncionalidadeList = recursoFuncionalidadeRepository.findAll();
        assertThat(recursoFuncionalidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRecursoFuncionalidadeWithPatch() throws Exception {
        // Initialize the database
        recursoFuncionalidadeRepository.saveAndFlush(recursoFuncionalidade);

        int databaseSizeBeforeUpdate = recursoFuncionalidadeRepository.findAll().size();

        // Update the recursoFuncionalidade using partial update
        RecursoFuncionalidade partialUpdatedRecursoFuncionalidade = new RecursoFuncionalidade();
        partialUpdatedRecursoFuncionalidade.setId(recursoFuncionalidade.getId());

        partialUpdatedRecursoFuncionalidade.idrf(UPDATED_IDRF).descricaoRequisito(UPDATED_DESCRICAO_REQUISITO).status(UPDATED_STATUS);

        restRecursoFuncionalidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecursoFuncionalidade.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRecursoFuncionalidade))
            )
            .andExpect(status().isOk());

        // Validate the RecursoFuncionalidade in the database
        List<RecursoFuncionalidade> recursoFuncionalidadeList = recursoFuncionalidadeRepository.findAll();
        assertThat(recursoFuncionalidadeList).hasSize(databaseSizeBeforeUpdate);
        RecursoFuncionalidade testRecursoFuncionalidade = recursoFuncionalidadeList.get(recursoFuncionalidadeList.size() - 1);
        assertThat(testRecursoFuncionalidade.getIdrf()).isEqualTo(UPDATED_IDRF);
        assertThat(testRecursoFuncionalidade.getDescricaoRequisito()).isEqualTo(UPDATED_DESCRICAO_REQUISITO);
        assertThat(testRecursoFuncionalidade.getPrioridade()).isEqualTo(DEFAULT_PRIORIDADE);
        assertThat(testRecursoFuncionalidade.getComplexibilidade()).isEqualTo(DEFAULT_COMPLEXIBILIDADE);
        assertThat(testRecursoFuncionalidade.getEsforco()).isEqualTo(DEFAULT_ESFORCO);
        assertThat(testRecursoFuncionalidade.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateRecursoFuncionalidadeWithPatch() throws Exception {
        // Initialize the database
        recursoFuncionalidadeRepository.saveAndFlush(recursoFuncionalidade);

        int databaseSizeBeforeUpdate = recursoFuncionalidadeRepository.findAll().size();

        // Update the recursoFuncionalidade using partial update
        RecursoFuncionalidade partialUpdatedRecursoFuncionalidade = new RecursoFuncionalidade();
        partialUpdatedRecursoFuncionalidade.setId(recursoFuncionalidade.getId());

        partialUpdatedRecursoFuncionalidade
            .idrf(UPDATED_IDRF)
            .descricaoRequisito(UPDATED_DESCRICAO_REQUISITO)
            .prioridade(UPDATED_PRIORIDADE)
            .complexibilidade(UPDATED_COMPLEXIBILIDADE)
            .esforco(UPDATED_ESFORCO)
            .status(UPDATED_STATUS);

        restRecursoFuncionalidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecursoFuncionalidade.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRecursoFuncionalidade))
            )
            .andExpect(status().isOk());

        // Validate the RecursoFuncionalidade in the database
        List<RecursoFuncionalidade> recursoFuncionalidadeList = recursoFuncionalidadeRepository.findAll();
        assertThat(recursoFuncionalidadeList).hasSize(databaseSizeBeforeUpdate);
        RecursoFuncionalidade testRecursoFuncionalidade = recursoFuncionalidadeList.get(recursoFuncionalidadeList.size() - 1);
        assertThat(testRecursoFuncionalidade.getIdrf()).isEqualTo(UPDATED_IDRF);
        assertThat(testRecursoFuncionalidade.getDescricaoRequisito()).isEqualTo(UPDATED_DESCRICAO_REQUISITO);
        assertThat(testRecursoFuncionalidade.getPrioridade()).isEqualTo(UPDATED_PRIORIDADE);
        assertThat(testRecursoFuncionalidade.getComplexibilidade()).isEqualTo(UPDATED_COMPLEXIBILIDADE);
        assertThat(testRecursoFuncionalidade.getEsforco()).isEqualTo(UPDATED_ESFORCO);
        assertThat(testRecursoFuncionalidade.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingRecursoFuncionalidade() throws Exception {
        int databaseSizeBeforeUpdate = recursoFuncionalidadeRepository.findAll().size();
        recursoFuncionalidade.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecursoFuncionalidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, recursoFuncionalidade.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(recursoFuncionalidade))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecursoFuncionalidade in the database
        List<RecursoFuncionalidade> recursoFuncionalidadeList = recursoFuncionalidadeRepository.findAll();
        assertThat(recursoFuncionalidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRecursoFuncionalidade() throws Exception {
        int databaseSizeBeforeUpdate = recursoFuncionalidadeRepository.findAll().size();
        recursoFuncionalidade.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecursoFuncionalidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(recursoFuncionalidade))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecursoFuncionalidade in the database
        List<RecursoFuncionalidade> recursoFuncionalidadeList = recursoFuncionalidadeRepository.findAll();
        assertThat(recursoFuncionalidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRecursoFuncionalidade() throws Exception {
        int databaseSizeBeforeUpdate = recursoFuncionalidadeRepository.findAll().size();
        recursoFuncionalidade.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecursoFuncionalidadeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(recursoFuncionalidade))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RecursoFuncionalidade in the database
        List<RecursoFuncionalidade> recursoFuncionalidadeList = recursoFuncionalidadeRepository.findAll();
        assertThat(recursoFuncionalidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRecursoFuncionalidade() throws Exception {
        // Initialize the database
        recursoFuncionalidadeRepository.saveAndFlush(recursoFuncionalidade);

        int databaseSizeBeforeDelete = recursoFuncionalidadeRepository.findAll().size();

        // Delete the recursoFuncionalidade
        restRecursoFuncionalidadeMockMvc
            .perform(delete(ENTITY_API_URL_ID, recursoFuncionalidade.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RecursoFuncionalidade> recursoFuncionalidadeList = recursoFuncionalidadeRepository.findAll();
        assertThat(recursoFuncionalidadeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

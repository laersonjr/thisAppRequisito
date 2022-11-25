package com.impulse.laerson.myapprequisito.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.impulse.laerson.myapprequisito.IntegrationTest;
import com.impulse.laerson.myapprequisito.domain.Funcionalidade;
import com.impulse.laerson.myapprequisito.repository.FuncionalidadeRepository;
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
 * Integration tests for the {@link FuncionalidadeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FuncionalidadeResourceIT {

    private static final String DEFAULT_FUNCIONALIDADE_PROJETO = "AAAAAAAAAA";
    private static final String UPDATED_FUNCIONALIDADE_PROJETO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/funcionalidades";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FuncionalidadeRepository funcionalidadeRepository;

    @Mock
    private FuncionalidadeRepository funcionalidadeRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFuncionalidadeMockMvc;

    private Funcionalidade funcionalidade;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Funcionalidade createEntity(EntityManager em) {
        Funcionalidade funcionalidade = new Funcionalidade().funcionalidadeProjeto(DEFAULT_FUNCIONALIDADE_PROJETO);
        return funcionalidade;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Funcionalidade createUpdatedEntity(EntityManager em) {
        Funcionalidade funcionalidade = new Funcionalidade().funcionalidadeProjeto(UPDATED_FUNCIONALIDADE_PROJETO);
        return funcionalidade;
    }

    @BeforeEach
    public void initTest() {
        funcionalidade = createEntity(em);
    }

    @Test
    @Transactional
    void createFuncionalidade() throws Exception {
        int databaseSizeBeforeCreate = funcionalidadeRepository.findAll().size();
        // Create the Funcionalidade
        restFuncionalidadeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(funcionalidade))
            )
            .andExpect(status().isCreated());

        // Validate the Funcionalidade in the database
        List<Funcionalidade> funcionalidadeList = funcionalidadeRepository.findAll();
        assertThat(funcionalidadeList).hasSize(databaseSizeBeforeCreate + 1);
        Funcionalidade testFuncionalidade = funcionalidadeList.get(funcionalidadeList.size() - 1);
        assertThat(testFuncionalidade.getFuncionalidadeProjeto()).isEqualTo(DEFAULT_FUNCIONALIDADE_PROJETO);
    }

    @Test
    @Transactional
    void createFuncionalidadeWithExistingId() throws Exception {
        // Create the Funcionalidade with an existing ID
        funcionalidade.setId(1L);

        int databaseSizeBeforeCreate = funcionalidadeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFuncionalidadeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(funcionalidade))
            )
            .andExpect(status().isBadRequest());

        // Validate the Funcionalidade in the database
        List<Funcionalidade> funcionalidadeList = funcionalidadeRepository.findAll();
        assertThat(funcionalidadeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFuncionalidadeProjetoIsRequired() throws Exception {
        int databaseSizeBeforeTest = funcionalidadeRepository.findAll().size();
        // set the field null
        funcionalidade.setFuncionalidadeProjeto(null);

        // Create the Funcionalidade, which fails.

        restFuncionalidadeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(funcionalidade))
            )
            .andExpect(status().isBadRequest());

        List<Funcionalidade> funcionalidadeList = funcionalidadeRepository.findAll();
        assertThat(funcionalidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFuncionalidades() throws Exception {
        // Initialize the database
        funcionalidadeRepository.saveAndFlush(funcionalidade);

        // Get all the funcionalidadeList
        restFuncionalidadeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(funcionalidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].funcionalidadeProjeto").value(hasItem(DEFAULT_FUNCIONALIDADE_PROJETO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFuncionalidadesWithEagerRelationshipsIsEnabled() throws Exception {
        when(funcionalidadeRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFuncionalidadeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(funcionalidadeRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFuncionalidadesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(funcionalidadeRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFuncionalidadeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(funcionalidadeRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getFuncionalidade() throws Exception {
        // Initialize the database
        funcionalidadeRepository.saveAndFlush(funcionalidade);

        // Get the funcionalidade
        restFuncionalidadeMockMvc
            .perform(get(ENTITY_API_URL_ID, funcionalidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(funcionalidade.getId().intValue()))
            .andExpect(jsonPath("$.funcionalidadeProjeto").value(DEFAULT_FUNCIONALIDADE_PROJETO));
    }

    @Test
    @Transactional
    void getNonExistingFuncionalidade() throws Exception {
        // Get the funcionalidade
        restFuncionalidadeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFuncionalidade() throws Exception {
        // Initialize the database
        funcionalidadeRepository.saveAndFlush(funcionalidade);

        int databaseSizeBeforeUpdate = funcionalidadeRepository.findAll().size();

        // Update the funcionalidade
        Funcionalidade updatedFuncionalidade = funcionalidadeRepository.findById(funcionalidade.getId()).get();
        // Disconnect from session so that the updates on updatedFuncionalidade are not directly saved in db
        em.detach(updatedFuncionalidade);
        updatedFuncionalidade.funcionalidadeProjeto(UPDATED_FUNCIONALIDADE_PROJETO);

        restFuncionalidadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFuncionalidade.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFuncionalidade))
            )
            .andExpect(status().isOk());

        // Validate the Funcionalidade in the database
        List<Funcionalidade> funcionalidadeList = funcionalidadeRepository.findAll();
        assertThat(funcionalidadeList).hasSize(databaseSizeBeforeUpdate);
        Funcionalidade testFuncionalidade = funcionalidadeList.get(funcionalidadeList.size() - 1);
        assertThat(testFuncionalidade.getFuncionalidadeProjeto()).isEqualTo(UPDATED_FUNCIONALIDADE_PROJETO);
    }

    @Test
    @Transactional
    void putNonExistingFuncionalidade() throws Exception {
        int databaseSizeBeforeUpdate = funcionalidadeRepository.findAll().size();
        funcionalidade.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuncionalidadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, funcionalidade.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(funcionalidade))
            )
            .andExpect(status().isBadRequest());

        // Validate the Funcionalidade in the database
        List<Funcionalidade> funcionalidadeList = funcionalidadeRepository.findAll();
        assertThat(funcionalidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFuncionalidade() throws Exception {
        int databaseSizeBeforeUpdate = funcionalidadeRepository.findAll().size();
        funcionalidade.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionalidadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(funcionalidade))
            )
            .andExpect(status().isBadRequest());

        // Validate the Funcionalidade in the database
        List<Funcionalidade> funcionalidadeList = funcionalidadeRepository.findAll();
        assertThat(funcionalidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFuncionalidade() throws Exception {
        int databaseSizeBeforeUpdate = funcionalidadeRepository.findAll().size();
        funcionalidade.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionalidadeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(funcionalidade)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Funcionalidade in the database
        List<Funcionalidade> funcionalidadeList = funcionalidadeRepository.findAll();
        assertThat(funcionalidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFuncionalidadeWithPatch() throws Exception {
        // Initialize the database
        funcionalidadeRepository.saveAndFlush(funcionalidade);

        int databaseSizeBeforeUpdate = funcionalidadeRepository.findAll().size();

        // Update the funcionalidade using partial update
        Funcionalidade partialUpdatedFuncionalidade = new Funcionalidade();
        partialUpdatedFuncionalidade.setId(funcionalidade.getId());

        partialUpdatedFuncionalidade.funcionalidadeProjeto(UPDATED_FUNCIONALIDADE_PROJETO);

        restFuncionalidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuncionalidade.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFuncionalidade))
            )
            .andExpect(status().isOk());

        // Validate the Funcionalidade in the database
        List<Funcionalidade> funcionalidadeList = funcionalidadeRepository.findAll();
        assertThat(funcionalidadeList).hasSize(databaseSizeBeforeUpdate);
        Funcionalidade testFuncionalidade = funcionalidadeList.get(funcionalidadeList.size() - 1);
        assertThat(testFuncionalidade.getFuncionalidadeProjeto()).isEqualTo(UPDATED_FUNCIONALIDADE_PROJETO);
    }

    @Test
    @Transactional
    void fullUpdateFuncionalidadeWithPatch() throws Exception {
        // Initialize the database
        funcionalidadeRepository.saveAndFlush(funcionalidade);

        int databaseSizeBeforeUpdate = funcionalidadeRepository.findAll().size();

        // Update the funcionalidade using partial update
        Funcionalidade partialUpdatedFuncionalidade = new Funcionalidade();
        partialUpdatedFuncionalidade.setId(funcionalidade.getId());

        partialUpdatedFuncionalidade.funcionalidadeProjeto(UPDATED_FUNCIONALIDADE_PROJETO);

        restFuncionalidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuncionalidade.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFuncionalidade))
            )
            .andExpect(status().isOk());

        // Validate the Funcionalidade in the database
        List<Funcionalidade> funcionalidadeList = funcionalidadeRepository.findAll();
        assertThat(funcionalidadeList).hasSize(databaseSizeBeforeUpdate);
        Funcionalidade testFuncionalidade = funcionalidadeList.get(funcionalidadeList.size() - 1);
        assertThat(testFuncionalidade.getFuncionalidadeProjeto()).isEqualTo(UPDATED_FUNCIONALIDADE_PROJETO);
    }

    @Test
    @Transactional
    void patchNonExistingFuncionalidade() throws Exception {
        int databaseSizeBeforeUpdate = funcionalidadeRepository.findAll().size();
        funcionalidade.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuncionalidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, funcionalidade.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(funcionalidade))
            )
            .andExpect(status().isBadRequest());

        // Validate the Funcionalidade in the database
        List<Funcionalidade> funcionalidadeList = funcionalidadeRepository.findAll();
        assertThat(funcionalidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFuncionalidade() throws Exception {
        int databaseSizeBeforeUpdate = funcionalidadeRepository.findAll().size();
        funcionalidade.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionalidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(funcionalidade))
            )
            .andExpect(status().isBadRequest());

        // Validate the Funcionalidade in the database
        List<Funcionalidade> funcionalidadeList = funcionalidadeRepository.findAll();
        assertThat(funcionalidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFuncionalidade() throws Exception {
        int databaseSizeBeforeUpdate = funcionalidadeRepository.findAll().size();
        funcionalidade.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionalidadeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(funcionalidade))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Funcionalidade in the database
        List<Funcionalidade> funcionalidadeList = funcionalidadeRepository.findAll();
        assertThat(funcionalidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFuncionalidade() throws Exception {
        // Initialize the database
        funcionalidadeRepository.saveAndFlush(funcionalidade);

        int databaseSizeBeforeDelete = funcionalidadeRepository.findAll().size();

        // Delete the funcionalidade
        restFuncionalidadeMockMvc
            .perform(delete(ENTITY_API_URL_ID, funcionalidade.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Funcionalidade> funcionalidadeList = funcionalidadeRepository.findAll();
        assertThat(funcionalidadeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

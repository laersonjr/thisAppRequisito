package com.impulse.laerson.myapprequisito.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.impulse.laerson.myapprequisito.IntegrationTest;
import com.impulse.laerson.myapprequisito.domain.Requisito;
import com.impulse.laerson.myapprequisito.repository.RequisitoRepository;
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
 * Integration tests for the {@link RequisitoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class RequisitoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/requisitos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RequisitoRepository requisitoRepository;

    @Mock
    private RequisitoRepository requisitoRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRequisitoMockMvc;

    private Requisito requisito;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Requisito createEntity(EntityManager em) {
        Requisito requisito = new Requisito().nome(DEFAULT_NOME);
        return requisito;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Requisito createUpdatedEntity(EntityManager em) {
        Requisito requisito = new Requisito().nome(UPDATED_NOME);
        return requisito;
    }

    @BeforeEach
    public void initTest() {
        requisito = createEntity(em);
    }

    @Test
    @Transactional
    void createRequisito() throws Exception {
        int databaseSizeBeforeCreate = requisitoRepository.findAll().size();
        // Create the Requisito
        restRequisitoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requisito)))
            .andExpect(status().isCreated());

        // Validate the Requisito in the database
        List<Requisito> requisitoList = requisitoRepository.findAll();
        assertThat(requisitoList).hasSize(databaseSizeBeforeCreate + 1);
        Requisito testRequisito = requisitoList.get(requisitoList.size() - 1);
        assertThat(testRequisito.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    void createRequisitoWithExistingId() throws Exception {
        // Create the Requisito with an existing ID
        requisito.setId(1L);

        int databaseSizeBeforeCreate = requisitoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequisitoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requisito)))
            .andExpect(status().isBadRequest());

        // Validate the Requisito in the database
        List<Requisito> requisitoList = requisitoRepository.findAll();
        assertThat(requisitoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = requisitoRepository.findAll().size();
        // set the field null
        requisito.setNome(null);

        // Create the Requisito, which fails.

        restRequisitoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requisito)))
            .andExpect(status().isBadRequest());

        List<Requisito> requisitoList = requisitoRepository.findAll();
        assertThat(requisitoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRequisitos() throws Exception {
        // Initialize the database
        requisitoRepository.saveAndFlush(requisito);

        // Get all the requisitoList
        restRequisitoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requisito.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRequisitosWithEagerRelationshipsIsEnabled() throws Exception {
        when(requisitoRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRequisitoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(requisitoRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRequisitosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(requisitoRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRequisitoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(requisitoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getRequisito() throws Exception {
        // Initialize the database
        requisitoRepository.saveAndFlush(requisito);

        // Get the requisito
        restRequisitoMockMvc
            .perform(get(ENTITY_API_URL_ID, requisito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(requisito.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    void getNonExistingRequisito() throws Exception {
        // Get the requisito
        restRequisitoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRequisito() throws Exception {
        // Initialize the database
        requisitoRepository.saveAndFlush(requisito);

        int databaseSizeBeforeUpdate = requisitoRepository.findAll().size();

        // Update the requisito
        Requisito updatedRequisito = requisitoRepository.findById(requisito.getId()).get();
        // Disconnect from session so that the updates on updatedRequisito are not directly saved in db
        em.detach(updatedRequisito);
        updatedRequisito.nome(UPDATED_NOME);

        restRequisitoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRequisito.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRequisito))
            )
            .andExpect(status().isOk());

        // Validate the Requisito in the database
        List<Requisito> requisitoList = requisitoRepository.findAll();
        assertThat(requisitoList).hasSize(databaseSizeBeforeUpdate);
        Requisito testRequisito = requisitoList.get(requisitoList.size() - 1);
        assertThat(testRequisito.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    void putNonExistingRequisito() throws Exception {
        int databaseSizeBeforeUpdate = requisitoRepository.findAll().size();
        requisito.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequisitoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requisito.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requisito))
            )
            .andExpect(status().isBadRequest());

        // Validate the Requisito in the database
        List<Requisito> requisitoList = requisitoRepository.findAll();
        assertThat(requisitoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRequisito() throws Exception {
        int databaseSizeBeforeUpdate = requisitoRepository.findAll().size();
        requisito.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequisitoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requisito))
            )
            .andExpect(status().isBadRequest());

        // Validate the Requisito in the database
        List<Requisito> requisitoList = requisitoRepository.findAll();
        assertThat(requisitoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRequisito() throws Exception {
        int databaseSizeBeforeUpdate = requisitoRepository.findAll().size();
        requisito.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequisitoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requisito)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Requisito in the database
        List<Requisito> requisitoList = requisitoRepository.findAll();
        assertThat(requisitoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRequisitoWithPatch() throws Exception {
        // Initialize the database
        requisitoRepository.saveAndFlush(requisito);

        int databaseSizeBeforeUpdate = requisitoRepository.findAll().size();

        // Update the requisito using partial update
        Requisito partialUpdatedRequisito = new Requisito();
        partialUpdatedRequisito.setId(requisito.getId());

        restRequisitoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequisito.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequisito))
            )
            .andExpect(status().isOk());

        // Validate the Requisito in the database
        List<Requisito> requisitoList = requisitoRepository.findAll();
        assertThat(requisitoList).hasSize(databaseSizeBeforeUpdate);
        Requisito testRequisito = requisitoList.get(requisitoList.size() - 1);
        assertThat(testRequisito.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    void fullUpdateRequisitoWithPatch() throws Exception {
        // Initialize the database
        requisitoRepository.saveAndFlush(requisito);

        int databaseSizeBeforeUpdate = requisitoRepository.findAll().size();

        // Update the requisito using partial update
        Requisito partialUpdatedRequisito = new Requisito();
        partialUpdatedRequisito.setId(requisito.getId());

        partialUpdatedRequisito.nome(UPDATED_NOME);

        restRequisitoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequisito.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequisito))
            )
            .andExpect(status().isOk());

        // Validate the Requisito in the database
        List<Requisito> requisitoList = requisitoRepository.findAll();
        assertThat(requisitoList).hasSize(databaseSizeBeforeUpdate);
        Requisito testRequisito = requisitoList.get(requisitoList.size() - 1);
        assertThat(testRequisito.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    void patchNonExistingRequisito() throws Exception {
        int databaseSizeBeforeUpdate = requisitoRepository.findAll().size();
        requisito.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequisitoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, requisito.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requisito))
            )
            .andExpect(status().isBadRequest());

        // Validate the Requisito in the database
        List<Requisito> requisitoList = requisitoRepository.findAll();
        assertThat(requisitoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRequisito() throws Exception {
        int databaseSizeBeforeUpdate = requisitoRepository.findAll().size();
        requisito.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequisitoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requisito))
            )
            .andExpect(status().isBadRequest());

        // Validate the Requisito in the database
        List<Requisito> requisitoList = requisitoRepository.findAll();
        assertThat(requisitoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRequisito() throws Exception {
        int databaseSizeBeforeUpdate = requisitoRepository.findAll().size();
        requisito.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequisitoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(requisito))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Requisito in the database
        List<Requisito> requisitoList = requisitoRepository.findAll();
        assertThat(requisitoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRequisito() throws Exception {
        // Initialize the database
        requisitoRepository.saveAndFlush(requisito);

        int databaseSizeBeforeDelete = requisitoRepository.findAll().size();

        // Delete the requisito
        restRequisitoMockMvc
            .perform(delete(ENTITY_API_URL_ID, requisito.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Requisito> requisitoList = requisitoRepository.findAll();
        assertThat(requisitoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

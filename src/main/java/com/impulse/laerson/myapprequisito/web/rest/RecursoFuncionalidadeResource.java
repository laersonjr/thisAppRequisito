package com.impulse.laerson.myapprequisito.web.rest;

import com.impulse.laerson.myapprequisito.domain.RecursoFuncionalidade;
import com.impulse.laerson.myapprequisito.repository.RecursoFuncionalidadeRepository;
import com.impulse.laerson.myapprequisito.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.impulse.laerson.myapprequisito.domain.RecursoFuncionalidade}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RecursoFuncionalidadeResource {

    private final Logger log = LoggerFactory.getLogger(RecursoFuncionalidadeResource.class);

    private static final String ENTITY_NAME = "recursoFuncionalidade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecursoFuncionalidadeRepository recursoFuncionalidadeRepository;

    public RecursoFuncionalidadeResource(RecursoFuncionalidadeRepository recursoFuncionalidadeRepository) {
        this.recursoFuncionalidadeRepository = recursoFuncionalidadeRepository;
    }

    /**
     * {@code POST  /recurso-funcionalidades} : Create a new recursoFuncionalidade.
     *
     * @param recursoFuncionalidade the recursoFuncionalidade to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recursoFuncionalidade, or with status {@code 400 (Bad Request)} if the recursoFuncionalidade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recurso-funcionalidades")
    public ResponseEntity<RecursoFuncionalidade> createRecursoFuncionalidade(
        @Valid @RequestBody RecursoFuncionalidade recursoFuncionalidade
    ) throws URISyntaxException {
        log.debug("REST request to save RecursoFuncionalidade : {}", recursoFuncionalidade);
        if (recursoFuncionalidade.getId() != null) {
            throw new BadRequestAlertException("A new recursoFuncionalidade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecursoFuncionalidade result = recursoFuncionalidadeRepository.save(recursoFuncionalidade);
        return ResponseEntity
            .created(new URI("/api/recurso-funcionalidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recurso-funcionalidades/:id} : Updates an existing recursoFuncionalidade.
     *
     * @param id the id of the recursoFuncionalidade to save.
     * @param recursoFuncionalidade the recursoFuncionalidade to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recursoFuncionalidade,
     * or with status {@code 400 (Bad Request)} if the recursoFuncionalidade is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recursoFuncionalidade couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recurso-funcionalidades/{id}")
    public ResponseEntity<RecursoFuncionalidade> updateRecursoFuncionalidade(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RecursoFuncionalidade recursoFuncionalidade
    ) throws URISyntaxException {
        log.debug("REST request to update RecursoFuncionalidade : {}, {}", id, recursoFuncionalidade);
        if (recursoFuncionalidade.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recursoFuncionalidade.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recursoFuncionalidadeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RecursoFuncionalidade result = recursoFuncionalidadeRepository.save(recursoFuncionalidade);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recursoFuncionalidade.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /recurso-funcionalidades/:id} : Partial updates given fields of an existing recursoFuncionalidade, field will ignore if it is null
     *
     * @param id the id of the recursoFuncionalidade to save.
     * @param recursoFuncionalidade the recursoFuncionalidade to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recursoFuncionalidade,
     * or with status {@code 400 (Bad Request)} if the recursoFuncionalidade is not valid,
     * or with status {@code 404 (Not Found)} if the recursoFuncionalidade is not found,
     * or with status {@code 500 (Internal Server Error)} if the recursoFuncionalidade couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/recurso-funcionalidades/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RecursoFuncionalidade> partialUpdateRecursoFuncionalidade(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RecursoFuncionalidade recursoFuncionalidade
    ) throws URISyntaxException {
        log.debug("REST request to partial update RecursoFuncionalidade partially : {}, {}", id, recursoFuncionalidade);
        if (recursoFuncionalidade.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recursoFuncionalidade.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recursoFuncionalidadeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RecursoFuncionalidade> result = recursoFuncionalidadeRepository
            .findById(recursoFuncionalidade.getId())
            .map(existingRecursoFuncionalidade -> {
                if (recursoFuncionalidade.getIdrf() != null) {
                    existingRecursoFuncionalidade.setIdrf(recursoFuncionalidade.getIdrf());
                }
                if (recursoFuncionalidade.getDescricaoRequisito() != null) {
                    existingRecursoFuncionalidade.setDescricaoRequisito(recursoFuncionalidade.getDescricaoRequisito());
                }
                if (recursoFuncionalidade.getPrioridade() != null) {
                    existingRecursoFuncionalidade.setPrioridade(recursoFuncionalidade.getPrioridade());
                }
                if (recursoFuncionalidade.getComplexibilidade() != null) {
                    existingRecursoFuncionalidade.setComplexibilidade(recursoFuncionalidade.getComplexibilidade());
                }
                if (recursoFuncionalidade.getEsforco() != null) {
                    existingRecursoFuncionalidade.setEsforco(recursoFuncionalidade.getEsforco());
                }
                if (recursoFuncionalidade.getStatus() != null) {
                    existingRecursoFuncionalidade.setStatus(recursoFuncionalidade.getStatus());
                }

                return existingRecursoFuncionalidade;
            })
            .map(recursoFuncionalidadeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recursoFuncionalidade.getId().toString())
        );
    }

    /**
     * {@code GET  /recurso-funcionalidades} : get all the recursoFuncionalidades.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recursoFuncionalidades in body.
     */
    @GetMapping("/recurso-funcionalidades")
    public ResponseEntity<List<RecursoFuncionalidade>> getAllRecursoFuncionalidades(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of RecursoFuncionalidades");
        Page<RecursoFuncionalidade> page;
        if (eagerload) {
            page = recursoFuncionalidadeRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = recursoFuncionalidadeRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /recurso-funcionalidades/:id} : get the "id" recursoFuncionalidade.
     *
     * @param id the id of the recursoFuncionalidade to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recursoFuncionalidade, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recurso-funcionalidades/{id}")
    public ResponseEntity<RecursoFuncionalidade> getRecursoFuncionalidade(@PathVariable Long id) {
        log.debug("REST request to get RecursoFuncionalidade : {}", id);
        Optional<RecursoFuncionalidade> recursoFuncionalidade = recursoFuncionalidadeRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(recursoFuncionalidade);
    }

    /**
     * {@code DELETE  /recurso-funcionalidades/:id} : delete the "id" recursoFuncionalidade.
     *
     * @param id the id of the recursoFuncionalidade to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recurso-funcionalidades/{id}")
    public ResponseEntity<Void> deleteRecursoFuncionalidade(@PathVariable Long id) {
        log.debug("REST request to delete RecursoFuncionalidade : {}", id);
        recursoFuncionalidadeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

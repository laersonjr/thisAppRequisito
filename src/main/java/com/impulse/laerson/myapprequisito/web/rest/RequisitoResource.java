package com.impulse.laerson.myapprequisito.web.rest;

import com.impulse.laerson.myapprequisito.domain.Requisito;
import com.impulse.laerson.myapprequisito.repository.RequisitoRepository;
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
 * REST controller for managing {@link com.impulse.laerson.myapprequisito.domain.Requisito}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RequisitoResource {

    private final Logger log = LoggerFactory.getLogger(RequisitoResource.class);

    private static final String ENTITY_NAME = "requisito";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequisitoRepository requisitoRepository;

    public RequisitoResource(RequisitoRepository requisitoRepository) {
        this.requisitoRepository = requisitoRepository;
    }

    /**
     * {@code POST  /requisitos} : Create a new requisito.
     *
     * @param requisito the requisito to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requisito, or with status {@code 400 (Bad Request)} if the requisito has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/requisitos")
    public ResponseEntity<Requisito> createRequisito(@Valid @RequestBody Requisito requisito) throws URISyntaxException {
        log.debug("REST request to save Requisito : {}", requisito);
        if (requisito.getId() != null) {
            throw new BadRequestAlertException("A new requisito cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Requisito result = requisitoRepository.save(requisito);
        return ResponseEntity
            .created(new URI("/api/requisitos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /requisitos/:id} : Updates an existing requisito.
     *
     * @param id the id of the requisito to save.
     * @param requisito the requisito to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requisito,
     * or with status {@code 400 (Bad Request)} if the requisito is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requisito couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/requisitos/{id}")
    public ResponseEntity<Requisito> updateRequisito(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Requisito requisito
    ) throws URISyntaxException {
        log.debug("REST request to update Requisito : {}, {}", id, requisito);
        if (requisito.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requisito.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requisitoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Requisito result = requisitoRepository.save(requisito);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requisito.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /requisitos/:id} : Partial updates given fields of an existing requisito, field will ignore if it is null
     *
     * @param id the id of the requisito to save.
     * @param requisito the requisito to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requisito,
     * or with status {@code 400 (Bad Request)} if the requisito is not valid,
     * or with status {@code 404 (Not Found)} if the requisito is not found,
     * or with status {@code 500 (Internal Server Error)} if the requisito couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/requisitos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Requisito> partialUpdateRequisito(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Requisito requisito
    ) throws URISyntaxException {
        log.debug("REST request to partial update Requisito partially : {}, {}", id, requisito);
        if (requisito.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requisito.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requisitoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Requisito> result = requisitoRepository
            .findById(requisito.getId())
            .map(existingRequisito -> {
                if (requisito.getNome() != null) {
                    existingRequisito.setNome(requisito.getNome());
                }

                return existingRequisito;
            })
            .map(requisitoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requisito.getId().toString())
        );
    }

    /**
     * {@code GET  /requisitos} : get all the requisitos.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requisitos in body.
     */
    @GetMapping("/requisitos")
    public ResponseEntity<List<Requisito>> getAllRequisitos(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Requisitos");
        Page<Requisito> page;
        if (eagerload) {
            page = requisitoRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = requisitoRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /requisitos/:id} : get the "id" requisito.
     *
     * @param id the id of the requisito to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requisito, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/requisitos/{id}")
    public ResponseEntity<Requisito> getRequisito(@PathVariable Long id) {
        log.debug("REST request to get Requisito : {}", id);
        Optional<Requisito> requisito = requisitoRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(requisito);
    }

    /**
     * {@code DELETE  /requisitos/:id} : delete the "id" requisito.
     *
     * @param id the id of the requisito to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/requisitos/{id}")
    public ResponseEntity<Void> deleteRequisito(@PathVariable Long id) {
        log.debug("REST request to delete Requisito : {}", id);
        requisitoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

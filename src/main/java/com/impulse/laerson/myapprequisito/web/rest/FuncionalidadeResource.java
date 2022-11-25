package com.impulse.laerson.myapprequisito.web.rest;

import com.impulse.laerson.myapprequisito.domain.Funcionalidade;
import com.impulse.laerson.myapprequisito.repository.FuncionalidadeRepository;
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
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.impulse.laerson.myapprequisito.domain.Funcionalidade}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FuncionalidadeResource {

    private final Logger log = LoggerFactory.getLogger(FuncionalidadeResource.class);

    private static final String ENTITY_NAME = "funcionalidade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FuncionalidadeRepository funcionalidadeRepository;

    public FuncionalidadeResource(FuncionalidadeRepository funcionalidadeRepository) {
        this.funcionalidadeRepository = funcionalidadeRepository;
    }

    /**
     * {@code POST  /funcionalidades} : Create a new funcionalidade.
     *
     * @param funcionalidade the funcionalidade to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new funcionalidade, or with status {@code 400 (Bad Request)} if the funcionalidade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/funcionalidades")
    public ResponseEntity<Funcionalidade> createFuncionalidade(@Valid @RequestBody Funcionalidade funcionalidade)
        throws URISyntaxException {
        log.debug("REST request to save Funcionalidade : {}", funcionalidade);
        if (funcionalidade.getId() != null) {
            throw new BadRequestAlertException("A new funcionalidade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Funcionalidade result = funcionalidadeRepository.save(funcionalidade);
        return ResponseEntity
            .created(new URI("/api/funcionalidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /funcionalidades/:id} : Updates an existing funcionalidade.
     *
     * @param id the id of the funcionalidade to save.
     * @param funcionalidade the funcionalidade to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated funcionalidade,
     * or with status {@code 400 (Bad Request)} if the funcionalidade is not valid,
     * or with status {@code 500 (Internal Server Error)} if the funcionalidade couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/funcionalidades/{id}")
    public ResponseEntity<Funcionalidade> updateFuncionalidade(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Funcionalidade funcionalidade
    ) throws URISyntaxException {
        log.debug("REST request to update Funcionalidade : {}, {}", id, funcionalidade);
        if (funcionalidade.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, funcionalidade.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!funcionalidadeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Funcionalidade result = funcionalidadeRepository.save(funcionalidade);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, funcionalidade.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /funcionalidades/:id} : Partial updates given fields of an existing funcionalidade, field will ignore if it is null
     *
     * @param id the id of the funcionalidade to save.
     * @param funcionalidade the funcionalidade to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated funcionalidade,
     * or with status {@code 400 (Bad Request)} if the funcionalidade is not valid,
     * or with status {@code 404 (Not Found)} if the funcionalidade is not found,
     * or with status {@code 500 (Internal Server Error)} if the funcionalidade couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/funcionalidades/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Funcionalidade> partialUpdateFuncionalidade(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Funcionalidade funcionalidade
    ) throws URISyntaxException {
        log.debug("REST request to partial update Funcionalidade partially : {}, {}", id, funcionalidade);
        if (funcionalidade.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, funcionalidade.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!funcionalidadeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Funcionalidade> result = funcionalidadeRepository
            .findById(funcionalidade.getId())
            .map(existingFuncionalidade -> {
                if (funcionalidade.getFuncionalidadeProjeto() != null) {
                    existingFuncionalidade.setFuncionalidadeProjeto(funcionalidade.getFuncionalidadeProjeto());
                }

                return existingFuncionalidade;
            })
            .map(funcionalidadeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, funcionalidade.getId().toString())
        );
    }

    /**
     * {@code GET  /funcionalidades} : get all the funcionalidades.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of funcionalidades in body.
     */
    @GetMapping("/funcionalidades")
    public List<Funcionalidade> getAllFuncionalidades(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Funcionalidades");
        if (eagerload) {
            return funcionalidadeRepository.findAllWithEagerRelationships();
        } else {
            return funcionalidadeRepository.findAll();
        }
    }

    /**
     * {@code GET  /funcionalidades/:id} : get the "id" funcionalidade.
     *
     * @param id the id of the funcionalidade to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the funcionalidade, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/funcionalidades/{id}")
    public ResponseEntity<Funcionalidade> getFuncionalidade(@PathVariable Long id) {
        log.debug("REST request to get Funcionalidade : {}", id);
        Optional<Funcionalidade> funcionalidade = funcionalidadeRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(funcionalidade);
    }

    /**
     * {@code DELETE  /funcionalidades/:id} : delete the "id" funcionalidade.
     *
     * @param id the id of the funcionalidade to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/funcionalidades/{id}")
    public ResponseEntity<Void> deleteFuncionalidade(@PathVariable Long id) {
        log.debug("REST request to delete Funcionalidade : {}", id);
        funcionalidadeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

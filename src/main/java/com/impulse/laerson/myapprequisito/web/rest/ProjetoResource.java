package com.impulse.laerson.myapprequisito.web.rest;

import com.impulse.laerson.myapprequisito.domain.Projeto;
import com.impulse.laerson.myapprequisito.repository.ProjetoRepository;
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
 * REST controller for managing {@link com.impulse.laerson.myapprequisito.domain.Projeto}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProjetoResource {

    private final Logger log = LoggerFactory.getLogger(ProjetoResource.class);

    private static final String ENTITY_NAME = "projeto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjetoRepository projetoRepository;

    public ProjetoResource(ProjetoRepository projetoRepository) {
        this.projetoRepository = projetoRepository;
    }

    /**
     * {@code POST  /projetos} : Create a new projeto.
     *
     * @param projeto the projeto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projeto, or with status {@code 400 (Bad Request)} if the projeto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/projetos")
    public ResponseEntity<Projeto> createProjeto(@Valid @RequestBody Projeto projeto) throws URISyntaxException {
        log.debug("REST request to save Projeto : {}", projeto);
        if (projeto.getId() != null) {
            throw new BadRequestAlertException("A new projeto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Projeto result = projetoRepository.save(projeto);
        return ResponseEntity
            .created(new URI("/api/projetos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /projetos/:id} : Updates an existing projeto.
     *
     * @param id the id of the projeto to save.
     * @param projeto the projeto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projeto,
     * or with status {@code 400 (Bad Request)} if the projeto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projeto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/projetos/{id}")
    public ResponseEntity<Projeto> updateProjeto(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Projeto projeto
    ) throws URISyntaxException {
        log.debug("REST request to update Projeto : {}, {}", id, projeto);
        if (projeto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projeto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projetoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Projeto result = projetoRepository.save(projeto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projeto.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /projetos/:id} : Partial updates given fields of an existing projeto, field will ignore if it is null
     *
     * @param id the id of the projeto to save.
     * @param projeto the projeto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projeto,
     * or with status {@code 400 (Bad Request)} if the projeto is not valid,
     * or with status {@code 404 (Not Found)} if the projeto is not found,
     * or with status {@code 500 (Internal Server Error)} if the projeto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/projetos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Projeto> partialUpdateProjeto(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Projeto projeto
    ) throws URISyntaxException {
        log.debug("REST request to partial update Projeto partially : {}, {}", id, projeto);
        if (projeto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projeto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projetoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Projeto> result = projetoRepository
            .findById(projeto.getId())
            .map(existingProjeto -> {
                if (projeto.getNome() != null) {
                    existingProjeto.setNome(projeto.getNome());
                }
                if (projeto.getDataDeCriacao() != null) {
                    existingProjeto.setDataDeCriacao(projeto.getDataDeCriacao());
                }
                if (projeto.getDataDeInicio() != null) {
                    existingProjeto.setDataDeInicio(projeto.getDataDeInicio());
                }
                if (projeto.getDataFim() != null) {
                    existingProjeto.setDataFim(projeto.getDataFim());
                }

                return existingProjeto;
            })
            .map(projetoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projeto.getId().toString())
        );
    }

    /**
     * {@code GET  /projetos} : get all the projetos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projetos in body.
     */
    @GetMapping("/projetos")
    public ResponseEntity<List<Projeto>> getAllProjetos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Projetos");
        Page<Projeto> page = projetoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /projetos/:id} : get the "id" projeto.
     *
     * @param id the id of the projeto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projeto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/projetos/{id}")
    public ResponseEntity<Projeto> getProjeto(@PathVariable Long id) {
        log.debug("REST request to get Projeto : {}", id);
        Optional<Projeto> projeto = projetoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(projeto);
    }

    /**
     * {@code DELETE  /projetos/:id} : delete the "id" projeto.
     *
     * @param id the id of the projeto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/projetos/{id}")
    public ResponseEntity<Void> deleteProjeto(@PathVariable Long id) {
        log.debug("REST request to delete Projeto : {}", id);
        projetoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

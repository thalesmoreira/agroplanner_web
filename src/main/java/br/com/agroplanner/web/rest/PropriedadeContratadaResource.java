package br.com.agroplanner.web.rest;

import br.com.agroplanner.service.PropriedadeContratadaService;
import br.com.agroplanner.web.rest.errors.BadRequestAlertException;
import br.com.agroplanner.service.dto.PropriedadeContratadaDTO;
import br.com.agroplanner.service.dto.PropriedadeContratadaCriteria;
import br.com.agroplanner.service.PropriedadeContratadaQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.agroplanner.domain.PropriedadeContratada}.
 */
@RestController
@RequestMapping("/api")
public class PropriedadeContratadaResource {

    private final Logger log = LoggerFactory.getLogger(PropriedadeContratadaResource.class);

    private static final String ENTITY_NAME = "propriedadeContratada";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PropriedadeContratadaService propriedadeContratadaService;

    private final PropriedadeContratadaQueryService propriedadeContratadaQueryService;

    public PropriedadeContratadaResource(PropriedadeContratadaService propriedadeContratadaService, PropriedadeContratadaQueryService propriedadeContratadaQueryService) {
        this.propriedadeContratadaService = propriedadeContratadaService;
        this.propriedadeContratadaQueryService = propriedadeContratadaQueryService;
    }

    /**
     * {@code POST  /propriedade-contratadas} : Create a new propriedadeContratada.
     *
     * @param propriedadeContratadaDTO the propriedadeContratadaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new propriedadeContratadaDTO, or with status {@code 400 (Bad Request)} if the propriedadeContratada has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/propriedade-contratadas")
    public ResponseEntity<PropriedadeContratadaDTO> createPropriedadeContratada(@Valid @RequestBody PropriedadeContratadaDTO propriedadeContratadaDTO) throws URISyntaxException {
        log.debug("REST request to save PropriedadeContratada : {}", propriedadeContratadaDTO);
        if (propriedadeContratadaDTO.getId() != null) {
            throw new BadRequestAlertException("A new propriedadeContratada cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PropriedadeContratadaDTO result = propriedadeContratadaService.save(propriedadeContratadaDTO);
        return ResponseEntity.created(new URI("/api/propriedade-contratadas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /propriedade-contratadas} : Updates an existing propriedadeContratada.
     *
     * @param propriedadeContratadaDTO the propriedadeContratadaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated propriedadeContratadaDTO,
     * or with status {@code 400 (Bad Request)} if the propriedadeContratadaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the propriedadeContratadaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/propriedade-contratadas")
    public ResponseEntity<PropriedadeContratadaDTO> updatePropriedadeContratada(@Valid @RequestBody PropriedadeContratadaDTO propriedadeContratadaDTO) throws URISyntaxException {
        log.debug("REST request to update PropriedadeContratada : {}", propriedadeContratadaDTO);
        if (propriedadeContratadaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PropriedadeContratadaDTO result = propriedadeContratadaService.save(propriedadeContratadaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, propriedadeContratadaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /propriedade-contratadas} : get all the propriedadeContratadas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of propriedadeContratadas in body.
     */
    @GetMapping("/propriedade-contratadas")
    public ResponseEntity<List<PropriedadeContratadaDTO>> getAllPropriedadeContratadas(PropriedadeContratadaCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get PropriedadeContratadas by criteria: {}", criteria);
        Page<PropriedadeContratadaDTO> page = propriedadeContratadaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /propriedade-contratadas/count} : count all the propriedadeContratadas.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/propriedade-contratadas/count")
    public ResponseEntity<Long> countPropriedadeContratadas(PropriedadeContratadaCriteria criteria) {
        log.debug("REST request to count PropriedadeContratadas by criteria: {}", criteria);
        return ResponseEntity.ok().body(propriedadeContratadaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /propriedade-contratadas/:id} : get the "id" propriedadeContratada.
     *
     * @param id the id of the propriedadeContratadaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the propriedadeContratadaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/propriedade-contratadas/{id}")
    public ResponseEntity<PropriedadeContratadaDTO> getPropriedadeContratada(@PathVariable Long id) {
        log.debug("REST request to get PropriedadeContratada : {}", id);
        Optional<PropriedadeContratadaDTO> propriedadeContratadaDTO = propriedadeContratadaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(propriedadeContratadaDTO);
    }

    /**
     * {@code DELETE  /propriedade-contratadas/:id} : delete the "id" propriedadeContratada.
     *
     * @param id the id of the propriedadeContratadaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/propriedade-contratadas/{id}")
    public ResponseEntity<Void> deletePropriedadeContratada(@PathVariable Long id) {
        log.debug("REST request to delete PropriedadeContratada : {}", id);
        propriedadeContratadaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

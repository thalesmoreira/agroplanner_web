package br.com.agroplanner.web.rest;

import br.com.agroplanner.service.Propriedade_caracteristicaService;
import br.com.agroplanner.web.rest.errors.BadRequestAlertException;
import br.com.agroplanner.service.dto.Propriedade_caracteristicaDTO;
import br.com.agroplanner.service.dto.Propriedade_caracteristicaCriteria;
import br.com.agroplanner.service.Propriedade_caracteristicaQueryService;

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
 * REST controller for managing {@link br.com.agroplanner.domain.Propriedade_caracteristica}.
 */
@RestController
@RequestMapping("/api")
public class Propriedade_caracteristicaResource {

    private final Logger log = LoggerFactory.getLogger(Propriedade_caracteristicaResource.class);

    private static final String ENTITY_NAME = "propriedade_caracteristica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Propriedade_caracteristicaService propriedade_caracteristicaService;

    private final Propriedade_caracteristicaQueryService propriedade_caracteristicaQueryService;

    public Propriedade_caracteristicaResource(Propriedade_caracteristicaService propriedade_caracteristicaService, Propriedade_caracteristicaQueryService propriedade_caracteristicaQueryService) {
        this.propriedade_caracteristicaService = propriedade_caracteristicaService;
        this.propriedade_caracteristicaQueryService = propriedade_caracteristicaQueryService;
    }

    /**
     * {@code POST  /propriedade-caracteristicas} : Create a new propriedade_caracteristica.
     *
     * @param propriedade_caracteristicaDTO the propriedade_caracteristicaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new propriedade_caracteristicaDTO, or with status {@code 400 (Bad Request)} if the propriedade_caracteristica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/propriedade-caracteristicas")
    public ResponseEntity<Propriedade_caracteristicaDTO> createPropriedade_caracteristica(@Valid @RequestBody Propriedade_caracteristicaDTO propriedade_caracteristicaDTO) throws URISyntaxException {
        log.debug("REST request to save Propriedade_caracteristica : {}", propriedade_caracteristicaDTO);
        if (propriedade_caracteristicaDTO.getId() != null) {
            throw new BadRequestAlertException("A new propriedade_caracteristica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Propriedade_caracteristicaDTO result = propriedade_caracteristicaService.save(propriedade_caracteristicaDTO);
        return ResponseEntity.created(new URI("/api/propriedade-caracteristicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /propriedade-caracteristicas} : Updates an existing propriedade_caracteristica.
     *
     * @param propriedade_caracteristicaDTO the propriedade_caracteristicaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated propriedade_caracteristicaDTO,
     * or with status {@code 400 (Bad Request)} if the propriedade_caracteristicaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the propriedade_caracteristicaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/propriedade-caracteristicas")
    public ResponseEntity<Propriedade_caracteristicaDTO> updatePropriedade_caracteristica(@Valid @RequestBody Propriedade_caracteristicaDTO propriedade_caracteristicaDTO) throws URISyntaxException {
        log.debug("REST request to update Propriedade_caracteristica : {}", propriedade_caracteristicaDTO);
        if (propriedade_caracteristicaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Propriedade_caracteristicaDTO result = propriedade_caracteristicaService.save(propriedade_caracteristicaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, propriedade_caracteristicaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /propriedade-caracteristicas} : get all the propriedade_caracteristicas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of propriedade_caracteristicas in body.
     */
    @GetMapping("/propriedade-caracteristicas")
    public ResponseEntity<List<Propriedade_caracteristicaDTO>> getAllPropriedade_caracteristicas(Propriedade_caracteristicaCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get Propriedade_caracteristicas by criteria: {}", criteria);
        Page<Propriedade_caracteristicaDTO> page = propriedade_caracteristicaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /propriedade-caracteristicas/count} : count all the propriedade_caracteristicas.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/propriedade-caracteristicas/count")
    public ResponseEntity<Long> countPropriedade_caracteristicas(Propriedade_caracteristicaCriteria criteria) {
        log.debug("REST request to count Propriedade_caracteristicas by criteria: {}", criteria);
        return ResponseEntity.ok().body(propriedade_caracteristicaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /propriedade-caracteristicas/:id} : get the "id" propriedade_caracteristica.
     *
     * @param id the id of the propriedade_caracteristicaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the propriedade_caracteristicaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/propriedade-caracteristicas/{id}")
    public ResponseEntity<Propriedade_caracteristicaDTO> getPropriedade_caracteristica(@PathVariable Long id) {
        log.debug("REST request to get Propriedade_caracteristica : {}", id);
        Optional<Propriedade_caracteristicaDTO> propriedade_caracteristicaDTO = propriedade_caracteristicaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(propriedade_caracteristicaDTO);
    }

    /**
     * {@code DELETE  /propriedade-caracteristicas/:id} : delete the "id" propriedade_caracteristica.
     *
     * @param id the id of the propriedade_caracteristicaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/propriedade-caracteristicas/{id}")
    public ResponseEntity<Void> deletePropriedade_caracteristica(@PathVariable Long id) {
        log.debug("REST request to delete Propriedade_caracteristica : {}", id);
        propriedade_caracteristicaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

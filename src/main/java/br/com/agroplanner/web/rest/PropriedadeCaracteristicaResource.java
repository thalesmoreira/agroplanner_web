package br.com.agroplanner.web.rest;

import br.com.agroplanner.service.PropriedadeCaracteristicaService;
import br.com.agroplanner.web.rest.errors.BadRequestAlertException;
import br.com.agroplanner.service.dto.PropriedadeCaracteristicaDTO;
import br.com.agroplanner.service.dto.PropriedadeCaracteristicaCriteria;
import br.com.agroplanner.service.PropriedadeCaracteristicaQueryService;

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
 * REST controller for managing {@link br.com.agroplanner.domain.PropriedadeCaracteristica}.
 */
@RestController
@RequestMapping("/api")
public class PropriedadeCaracteristicaResource {

    private final Logger log = LoggerFactory.getLogger(PropriedadeCaracteristicaResource.class);

    private static final String ENTITY_NAME = "propriedade_caracteristica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PropriedadeCaracteristicaService propriedadeCaracteristicaService;

    private final PropriedadeCaracteristicaQueryService propriedadeCaracteristicaQueryService;

    public PropriedadeCaracteristicaResource(PropriedadeCaracteristicaService propriedadeCaracteristicaService, PropriedadeCaracteristicaQueryService propriedadeCaracteristicaQueryService) {
        this.propriedadeCaracteristicaService = propriedadeCaracteristicaService;
        this.propriedadeCaracteristicaQueryService = propriedadeCaracteristicaQueryService;
    }

    /**
     * {@code POST  /propriedade-caracteristicas} : Create a new propriedadeCaracteristica.
     *
     * @param propriedadeCaracteristicaDTO the propriedadeCaracteristicaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new propriedadeCaracteristicaDTO, or with status {@code 400 (Bad Request)} if the propriedadeCaracteristica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/propriedade-caracteristicas")
    public ResponseEntity<PropriedadeCaracteristicaDTO> createPropriedade_caracteristica(@Valid @RequestBody PropriedadeCaracteristicaDTO propriedadeCaracteristicaDTO) throws URISyntaxException {
        log.debug("REST request to save PropriedadeCaracteristica : {}", propriedadeCaracteristicaDTO);
        if (propriedadeCaracteristicaDTO.getId() != null) {
            throw new BadRequestAlertException("A new propriedadeCaracteristica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PropriedadeCaracteristicaDTO result = propriedadeCaracteristicaService.save(propriedadeCaracteristicaDTO);
        return ResponseEntity.created(new URI("/api/propriedade-caracteristicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /propriedade-caracteristicas} : Updates an existing propriedadeCaracteristica.
     *
     * @param propriedadeCaracteristicaDTO the propriedadeCaracteristicaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated propriedadeCaracteristicaDTO,
     * or with status {@code 400 (Bad Request)} if the propriedadeCaracteristicaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the propriedadeCaracteristicaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/propriedade-caracteristicas")
    public ResponseEntity<PropriedadeCaracteristicaDTO> updatePropriedade_caracteristica(@Valid @RequestBody PropriedadeCaracteristicaDTO propriedadeCaracteristicaDTO) throws URISyntaxException {
        log.debug("REST request to update PropriedadeCaracteristica : {}", propriedadeCaracteristicaDTO);
        if (propriedadeCaracteristicaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PropriedadeCaracteristicaDTO result = propriedadeCaracteristicaService.save(propriedadeCaracteristicaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, propriedadeCaracteristicaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /propriedade-caracteristicas} : get all the propriedadeCaracteristicas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of propriedadeCaracteristicas in body.
     */
    @GetMapping("/propriedade-caracteristicas")
    public ResponseEntity<List<PropriedadeCaracteristicaDTO>> getAllPropriedadeCaracteristicas(PropriedadeCaracteristicaCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get PropriedadeCaracteristicas by criteria: {}", criteria);
        Page<PropriedadeCaracteristicaDTO> page = propriedadeCaracteristicaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /propriedade-caracteristicas/count} : count all the propriedadeCaracteristicas.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/propriedade-caracteristicas/count")
    public ResponseEntity<Long> countPropriedadeCaracteristicas(PropriedadeCaracteristicaCriteria criteria) {
        log.debug("REST request to count PropriedadeCaracteristicas by criteria: {}", criteria);
        return ResponseEntity.ok().body(propriedadeCaracteristicaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /propriedade-caracteristicas/:id} : get the "id" propriedadeCaracteristica.
     *
     * @param id the id of the propriedadeCaracteristicaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the propriedadeCaracteristicaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/propriedade-caracteristicas/{id}")
    public ResponseEntity<PropriedadeCaracteristicaDTO> getPropriedade_caracteristica(@PathVariable Long id) {
        log.debug("REST request to get PropriedadeCaracteristica : {}", id);
        Optional<PropriedadeCaracteristicaDTO> propriedadeCaracteristicaDTO = propriedadeCaracteristicaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(propriedadeCaracteristicaDTO);
    }

    /**
     * {@code DELETE  /propriedade-caracteristicas/:id} : delete the "id" propriedadeCaracteristica.
     *
     * @param id the id of the propriedadeCaracteristicaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/propriedade-caracteristicas/{id}")
    public ResponseEntity<Void> deletePropriedade_caracteristica(@PathVariable Long id) {
        log.debug("REST request to delete PropriedadeCaracteristica : {}", id);
        propriedadeCaracteristicaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
